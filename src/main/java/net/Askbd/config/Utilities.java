package net.Askbd.config;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.Askbd.documents.Content;
import net.Askbd.documents.ContentSummary;

public class Utilities {
    private static final char[] BENGALI_DIGITS = {'০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯'};
    
    // Map of English month names to Bangla month names
    private static final HashMap<String, String> MONTHS_BANGLA = new HashMap<>();
    private static final HashMap<String, String> CATEGORY_BANGLA = new HashMap<>();
    static {
        MONTHS_BANGLA.put("January", "জানুয়ারি");
        MONTHS_BANGLA.put("February", "ফেব্রুয়ারি");
        MONTHS_BANGLA.put("March", "মার্চ");
        MONTHS_BANGLA.put("April", "এপ্রিল");
        MONTHS_BANGLA.put("May", "মে");
        MONTHS_BANGLA.put("June", "জুন");
        MONTHS_BANGLA.put("July", "জুলাই");
        MONTHS_BANGLA.put("August", "আগস্ট");
        MONTHS_BANGLA.put("September", "সেপ্টেম্বর");
        MONTHS_BANGLA.put("October", "অক্টোবর");
        MONTHS_BANGLA.put("November", "নভেম্বর");
        MONTHS_BANGLA.put("December", "ডিসেম্বর");

        CATEGORY_BANGLA.put("lifestyle", "জীবন-যাপন");
        CATEGORY_BANGLA.put("tech", "বিজ্ঞান-প্রযুক্তি");
        CATEGORY_BANGLA.put("history", "ইতিহাস");
        CATEGORY_BANGLA.put("health", "স্বাস্থ্য");
        CATEGORY_BANGLA.put("national", "বাংলাদেশ");
        CATEGORY_BANGLA.put("world", "বিশ্ব");
        CATEGORY_BANGLA.put("recent", "সাম্প্রতিক");
        CATEGORY_BANGLA.put("fiction", "ফিকশন");
        CATEGORY_BANGLA.put("opinion", "মতামত");
        CATEGORY_BANGLA.put("interview", "সাক্ষাৎকার");
    }

    // Convert English digits to Bangla numerals
    public static String convertToBanglaNumerals(String input) {
        StringBuilder banglaNumber = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isDigit(ch)) {
                banglaNumber.append(BENGALI_DIGITS[ch - '0']);
            } else {
                banglaNumber.append(ch); // Append non-digit characters as is
            }
        }
        return banglaNumber.toString();
    }

    // Translate LocalDateTime to Bangla format
    public static String translateLocalDateTimeToBangla(LocalDateTime dateTime) {
        // Define the formatter for the desired date and time format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        // Format the date and time
        String formattedDate = dateTime.format(dateFormatter);
        String formattedTime = dateTime.format(timeFormatter);

        // Extract the month name and translate it to Bangla
        String month = formattedDate.split(" ")[1];
        String banglaMonth = MONTHS_BANGLA.getOrDefault(month, month); // Default to English if not found

        // Convert day, year, and time to Bangla numerals
        String banglaDate = formattedDate.replace(formattedDate.split(" ")[0], convertToBanglaNumerals(formattedDate.split(" ")[0]))
                                         .replace(formattedDate.split(" ")[2], convertToBanglaNumerals(formattedDate.split(" ")[2]));

        //String banglaTime = convertToBanglaNumerals(formattedTime);
        //return banglaDate.replace(month, banglaMonth) + " " + banglaTime;
        return banglaDate.replace(month, banglaMonth);
    }
    // Translate date to Bangla
    public static String translateDateToBangla(Date date) {
        // Format the date into day, month, and year
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.ENGLISH);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

        // Extract components
        String day = dayFormat.format(date);
        String month = monthFormat.format(date);
        String year = yearFormat.format(date);

        // Convert to Bangla
        String banglaDay = convertToBanglaNumerals(day);
        String banglaMonth = MONTHS_BANGLA.getOrDefault(month, month); // Default to English if not found
        String banglaYear = convertToBanglaNumerals(year);

        // Combine into Bangla format
        return banglaDay + " " + banglaMonth + " " + banglaYear;
    }

    public static void convertIntoBangla(List<ContentSummary> contentSummaries) {
        for(ContentSummary contentSummary: contentSummaries) {
            contentSummary.setVisitedView("পোস্টটি "+convertToBanglaNumerals(String.valueOf(contentSummary.getVisited()))+" বার পড়া হয়েছে");
            contentSummary.setCategoryView(CATEGORY_BANGLA.get(contentSummary.getCategory()));
            contentSummary.setCreatedAtView(translateLocalDateTimeToBangla(contentSummary.getCreatedAt()));
        }
    }
    
    public static void convertIntoBangla(Content content) {
        content.setVisitedView("পোস্টটি "+convertToBanglaNumerals(String.valueOf(content.getVisited()))+" বার পড়া হয়েছে");
        content.setCategoryView(CATEGORY_BANGLA.get(content.getCategory()));
        content.setCreatedAtView(translateLocalDateTimeToBangla(content.getCreatedAt())+ " পোস্ট করা হয়েছে");
    }
}
