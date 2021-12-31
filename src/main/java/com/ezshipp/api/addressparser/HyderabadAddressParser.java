package com.ezshipp.api.addressparser;



import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.ezshipp.api.model.Address;

import static org.apache.commons.lang3.math.NumberUtils.isDigits;
public class HyderabadAddressParser {
    private Address address;
    private List<String> addressWords;

    public HyderabadAddressParser(Address address) {
        this.address = address;
        addressWords = new ArrayList<>();
    }

    public Address parseAddress() throws Exception   {
        if (address == null) {
            throw new Exception();
        }

        List<String> googlePlacesList = new ArrayList<>();
        if (!StringUtils.isEmpty(address.getAddressString())) {
            String addressStr = address.getAddressString();
            addressStr = addressStr.trim();
            addressStr = addressStr.replace(",,", ",");
            addressStr = addressStr.replace("Flot", "Flat");
            addressStr = addressStr.replace(" - ", "-");

            //StringTokenizer commaTokens = new StringTokenizer(addressStr, ",//-");
            String[] tokens = addressStr.split("[\\s-,;.]+");
            for (String token : tokens) {
                if (!isDigits(token)  && !token.matches(".*\\d.*")) {
                    if (!isHyderabad(token) && !isTelangana(token)) {
                        googlePlacesList.add(token);
                    } else {
                        if (isHyderabad(token)) {
                        		//|| Localities.getLocalities().contains(token)) {
                            address.setCity(token);
                        }
                        if (isTelangana(token)) {
                            address.setState(token);
                        }
                    }
                }

                if (token.matches(".*\\d.*")) {
                    String apartment = extractApartmentNumber(token, address);
                    if (!StringUtils.isEmpty(apartment)) {
                        if (!StringUtils.isEmpty(address.getApartmentNumber())) {
                            address.setApartmentNumber(address.getApartmentNumber().
                                    concat(", ").
                                    concat(apartment));
                        } else {
                            address.setApartmentNumber(apartment);
                        }
                    }
                }

                if (isDigits(token)) {
                    extractPincode(token, address);
                }
            }
        }

        address.setSearchList(googlePlacesList);

        return address;
    }

    private void tokenize(StringTokenizer tokenizer) {
        while (tokenizer.hasMoreTokens())   {
            addressWords.add(tokenizer.nextToken());
        }
    }

    private boolean isHyderabad(String token) {
        if (!StringUtils.isEmpty(token) && token.equalsIgnoreCase("Hyderabad")) {
            return true;
        }
        return false;
    }

    private boolean isTelangana(String token) {
        if (!StringUtils.isEmpty(token) && token.equalsIgnoreCase("Telangana")) {
            return true;
        }
        return false;
    }

    public void extractPincode(String token, Address address) {
        if (isDigits(token) && token.length() == 6 && token.startsWith("5")) {
            address.setPinCode(token);
        }
    }

    public String extractApartmentNumber(String token, Address address) {
        String flatNumber = "";

        if (token.toUpperCase().contains("SURVEY")) {
            return flatNumber;
        }

        boolean match = false;
        boolean startsWithFlat = containsFlat(token);
        if (startsWithFlat) {
            flatNumber = token;
        }

        if (!isDigits(token) && !token.contains("-")) {
            char ch = token.charAt(0);
            if (!Character.isDigit(ch)) {
                flatNumber = token;
            }
        }

        if (StringUtils.isEmpty(flatNumber)) {
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(token);
            while (m.find()) {
                if (startsWithFlat) {
                    flatNumber = m.group();
                    String extractAptName = token.substring(token.indexOf(flatNumber) + flatNumber.length());
                    if (!StringUtils.isEmpty(extractAptName)) {
                        address.getSearchList().add(extractAptName.trim());
                    }
                }
            }
        }


        return flatNumber.toUpperCase();
    }

    private boolean containsFlat(String token) {
        if (token.contains("Flat") ||
                token.contains("Flat No-") ||
                token.startsWith("Flat:") ||
                token.startsWith("Flat No:") ||
                token.contains("Flat No:") ||
                token.startsWith("Flat Number") ||
                token.contains("Flat Number") ||
                token.contains("Flat Number:") ||
                token.contains("Flat Number-") ||
                token.contains("apt") ||
                token.startsWith("apartment") ||
                token.startsWith("apt")) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) throws Exception {
        //Address address = new Address("107, vasudev bloomfield ecstasy, Tellapur, Hyderabad");
        //Address address = new Address("Plot no: 169/30,Flat -101, Sterling homes, HUDA layout, Lane beside Ratnadeep super market, Nallagandla, Serilingampalli,Hyderabad,500019");
        //Address address = new Address("Flat No 204, Block-18, My Home Vihanga, Near Q-City, Near Wipro Circle, Gachibowli");
        //Address address = new Address("d-1501,ramky towers, gachibowli,hyderabad");
        //Address address = new Address("Flot.no702 7th floor Lodha Meridian A Block opp. RTO Office Phase no 5 KPHB ");
        Address address = new Address("Pulses, Divyasree NSL SEZ Campus, Raidurg, Gachibowli - 500032, Hyderabad, Telangana");
        HyderabadAddressParser parser = new HyderabadAddressParser(address);
        List<String> searchList = parser.parseAddress().getSearchList();
        for (String s : searchList) {
            System.out.println(s);
        }
        System.out.println("apartment: " + address.getApartmentNumber());
        System.out.println("city: " + address.getCity());
        System.out.println("pincode: " + address.getPinCode());
    }

}
