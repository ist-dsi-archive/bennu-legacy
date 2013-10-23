/* 
* @(#)Address.java 
* 
* Copyright 2009 Instituto Superior Tecnico 
* Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes 
*  
*      https://fenix-ashes.ist.utl.pt/ 
*  
*   This file is part of the Bennu Web Application Infrastructure. 
* 
*   The Bennu Web Application Infrastructure is free software: you can 
*   redistribute it and/or modify it under the terms of the GNU Lesser General 
*   Public License as published by the Free Software Foundation, either version  
*   3 of the License, or (at your option) any later version. 
* 
*   Bennu is distributed in the hope that it will be useful, 
*   but WITHOUT ANY WARRANTY; without even the implied warranty of 
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
*   GNU Lesser General Public License for more details. 
* 
*   You should have received a copy of the GNU Lesser General Public License 
*   along with Bennu. If not, see <http://www.gnu.org/licenses/>. 
*  
*/
package pt.ist.bennu.core.util.legacy;

import java.io.Serializable;

import pt.ist.bennu.core.domain.exceptions.DomainException;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class Address implements Serializable {

    private String line1;
    private String line2;
    private String postalCode;
    private String location;
    private String country;

    @SuppressWarnings("serial")
    private class AddressException extends DomainException {

        protected AddressException(String key, String... args) {
            super(null, key, args);
        }

    }

    public Address(final String line1, final String line2, final String postalCode, final String location, final String country) {
        setLine1(line1);
        setLine2(line2);
        setLocation(location);
        setCountry(country);
        setPostalCode(postalCode);
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getLocation() {
        return location;
    }

    public String getCountry() {
        return country;
    }

    protected void setLine1(String line1) {
        if (line1 == null || line1.isEmpty()) {
            throw new AddressException("error.address.line1.cannot.be.empty");
        }
        this.line1 = line1;
    }

    protected void setLine2(String line2) {
        this.line2 = line2;
    }

    protected void setPostalCode(String postalCode) {
        if (postalCode == null || postalCode.isEmpty()) {
            throw new AddressException("error.address.postalCode.cannot.be.empty");
        }
        this.postalCode = postalCode;
    }

    protected void setLocation(String location) {
        if (location == null || location.isEmpty()) {
            throw new AddressException("error.address.location.cannot.be.empty");
        }
        this.location = location;
    }

    protected void setCountry(String country) {
        if (country == null || country.isEmpty()) {
            throw new AddressException("error.address.country.cannot.be.empty");
        }
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    public boolean equals(final Address address) {
        return address != null && match(getLine1(), address.getLine1()) && match(getLine2(), address.getLine2())
                && match(getPostalCode(), address.getPostalCode()) && match(getLocation(), address.getLocation())
                && match(getCountry(), address.getCountry());
    }

    private boolean match(final String string1, final String string2) {
        return (string1 == null && string2 == null) || (string1 != null && string1.equals(string2));
    }

    static final String CHARACTER = ":";
    static final String SEPARATOR = CHARACTER + CHARACTER;

    public String exportAsString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(escapeFieldSeparator(getLine1()));
        builder.append(SEPARATOR);
        builder.append(escapeFieldSeparator(getLine2()));
        builder.append(SEPARATOR);
        builder.append(escapeFieldSeparator(getPostalCode()));
        builder.append(SEPARATOR);
        builder.append(escapeFieldSeparator(getLocation()));
        builder.append(SEPARATOR);
        builder.append(escapeFieldSeparator(getCountry()));
        return builder.toString();
    }

    private String escapeFieldSeparator(String field) {
        return field != null ? field.replaceAll(CHARACTER, "\\\\" + CHARACTER + "\\\\") : "";
    }

    public static Address importFromString(String string) {
        String[] split = string.split(SEPARATOR);
        return new Address(removeEscapeFieldSeparator(split[0]), removeEscapeFieldSeparator(split[1]),
                removeEscapeFieldSeparator(split[2]), removeEscapeFieldSeparator(split[3]), removeEscapeFieldSeparator(split[4]));
    }

    private static String removeEscapeFieldSeparator(String field) {
        return field != null ? field.replaceAll("\\\\" + CHARACTER + "\\\\", CHARACTER) : null;
    }

}