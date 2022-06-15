package ru.animalcare.validator;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomEmailValidator implements Serializable {

    private static final long serialVersionUID = 1705927040799295880L;

    private static final String SPECIAL_CHARS = "\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]";
    private static final String VALID_CHARS = "(\\\\.)|[^\\s" + SPECIAL_CHARS + "]";
    private static final String QUOTED_USER = "(\"(\\\\\"|[^\"])*\")";
    private static final String WORD = "((" + VALID_CHARS + "|')+|" + QUOTED_USER + ")";

    private static final String EMAIL_REGEX = "^(.+)@(\\S+)$";
    private static final String IP_DOMAIN_REGEX = "^\\[(.*)\\]$";
    private static final String USER_REGEX = "^" + WORD + "(\\." + WORD + ")*$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern IP_DOMAIN_PATTERN = Pattern.compile(IP_DOMAIN_REGEX);
    private static final Pattern USER_PATTERN = Pattern.compile(USER_REGEX);

    private static final int MAX_USERNAME_LEN = 64;

    private final boolean allowTld;

    /**
     * Singleton instance of this class, which
     *  doesn't consider local addresses as valid.
     */
    private static final CustomEmailValidator EMAIL_VALIDATOR = new CustomEmailValidator(false, false);

    /**
     * Singleton instance of this class, which
     *  doesn't consider local addresses as valid.
     */
    private static final CustomEmailValidator EMAIL_VALIDATOR_WITH_TLD = new CustomEmailValidator(false, true);

    /**
     * Singleton instance of this class, which does
     *  consider local addresses valid.
     */
    private static final CustomEmailValidator EMAIL_VALIDATOR_WITH_LOCAL = new CustomEmailValidator(true, false);


    /**
     * Singleton instance of this class, which does
     *  consider local addresses valid.
     */
    private static final CustomEmailValidator EMAIL_VALIDATOR_WITH_LOCAL_WITH_TLD = new CustomEmailValidator(true, true);

    private final DomainValidator domainValidator;

    /**
     * Returns the Singleton instance of this validator.
     *
     * @return singleton instance of this validator.
     */
    public static CustomEmailValidator getInstance() {
        return EMAIL_VALIDATOR;
    }

    /**
     * Returns the Singleton instance of this validator,
     *  with local validation as required.
     *
     * @param allowLocal Should local addresses be considered valid?
     * @param allowTld Should TLDs be allowed?
     * @return singleton instance of this validator
     */
    public static CustomEmailValidator getInstance(boolean allowLocal, boolean allowTld) {
        if(allowLocal) {
            if (allowTld) {
                return EMAIL_VALIDATOR_WITH_LOCAL_WITH_TLD;
            } else {
                return EMAIL_VALIDATOR_WITH_LOCAL;
            }
        } else {
            if (allowTld) {
                return EMAIL_VALIDATOR_WITH_TLD;
            } else {
                return EMAIL_VALIDATOR;
            }
        }
    }

    /**
     * Returns the Singleton instance of this validator,
     *  with local validation as required.
     *
     * @param allowLocal Should local addresses be considered valid?
     * @return singleton instance of this validator
     */
    public static CustomEmailValidator getInstance(boolean allowLocal) {
        return getInstance(allowLocal, false);
    }

    /**
     * constructor for creating instances with the specified domainValidator
     *
     * @param allowLocal Should local addresses be considered valid?
     * @param allowTld Should TLDs be allowed?
     * @param domainValidator allow override of the DomainValidator.
     * The instance must have the same allowLocal setting.
     * @since 1.7
     */
    public CustomEmailValidator(boolean allowLocal, boolean allowTld, DomainValidator domainValidator) {
        super();
        this.allowTld = allowTld;
        if (domainValidator == null) {
            throw new IllegalArgumentException("DomainValidator cannot be null");
        } else {
            if (domainValidator.isAllowLocal() != allowLocal) {
                throw new IllegalArgumentException("DomainValidator must agree with allowLocal setting");
            }
            this.domainValidator = domainValidator;
        }
    }

    /**
     * Protected constructor for subclasses to use.
     *
     * @param allowLocal Should local addresses be considered valid?
     * @param allowTld Should TLDs be allowed?
     */
    protected CustomEmailValidator(boolean allowLocal, boolean allowTld) {
        this.allowTld = allowTld;
        this.domainValidator = DomainValidator.getInstance(allowLocal);
    }

    /**
     * Protected constructor for subclasses to use.
     *
     * @param allowLocal Should local addresses be considered valid?
     */
    protected CustomEmailValidator(boolean allowLocal) {
        this(allowLocal, false);
    }

    /**
     * <p>Checks if a field has a valid e-mail address.</p>
     *
     * @param email The value validation is being performed on.  A <code>null</code>
     *              value is considered invalid.
     * @return true if the email address is valid.
     */
    public boolean isValid(String email) {
        if (email == null) {
            return false;
        }

        if (email.endsWith(".")) { // check this first - it's cheap!
            return false;
        }

        if(email.startsWith(".")){
            return false;
        }


        // Check the whole email address structure
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        if (!emailMatcher.matches()) {
            return false;
        }

        if (!isValidUser(emailMatcher.group(1))) {
            return false;
        }

        if (!isValidDomain(emailMatcher.group(2))) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if the domain component of an email address is valid.
     *
     * @param domain being validated, may be in IDN format
     * @return true if the email address's domain is valid.
     */
    protected boolean isValidDomain(String domain) {
        // see if domain is an IP address in brackets
        Matcher ipDomainMatcher = IP_DOMAIN_PATTERN.matcher(domain);

        if (ipDomainMatcher.matches()) {
            InetAddressValidator inetAddressValidator =
                    InetAddressValidator.getInstance();
            return inetAddressValidator.isValid(ipDomainMatcher.group(1));
        }
        // Domain is symbolic name
        if (allowTld) {
            return domainValidator.isValid(domain) || (!domain.startsWith(".") && domainValidator.isValidTld(domain));
        } else {
            return domainValidator.isValid(domain);
        }
    }

    /**
     * Returns true if the user component of an email address is valid.
     *
     * @param user being validated
     * @return true if the user name is valid.
     */
    protected boolean isValidUser(String user) {

        if (user == null || user.length() > MAX_USERNAME_LEN) {
            return false;
        }

        return USER_PATTERN.matcher(user).matches();
    }

}