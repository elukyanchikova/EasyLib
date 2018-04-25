package storage;

import documents.Book;
import documents.Document;
import documents.JournalArticle;

import java.util.Arrays;
import java.util.function.Predicate;

public class Filter {

    public String title;
    public String documentType;
    public String authors;
    public String keywords;
    public Integer minPrice;
    public Integer maxPrice;
    public String edition;
    public String publisher;
    public String publicationMonth;
    public Integer publicationYear;
    public String journalName;
    public String editor;
    public Boolean isBestseller;
    public Boolean isAvailable;
    public boolean conjunction;

    //TODO: have a copy, bestSell, edition
    //TODO: publication Month for journal

    private boolean stringMatches(String query, String string) {
        if (query == null || string == null) {
            return true;
        }
        if (string.equals("none")) {
            string = "";
        }
        if (string.isEmpty()) {
            return true;
        }

        String split = " ";
        String[] queryElements = query.split(split);
        if (queryElements.length == 0) {
            return true;
        }
        String[] stringElements = string.split(split);

        Predicate<String> inString = (q -> Arrays.stream(stringElements).anyMatch(s -> s.contains(q)));

        if (conjunction) {
            return Arrays.stream(queryElements).allMatch(inString);
        }
        return Arrays.stream(queryElements).anyMatch(inString);
    }

    public boolean filter(Document document) {
        if (!stringMatches(title, document.title)) return false;
        if (minPrice != null && document.price < minPrice) return false;
        if (maxPrice != null && document.price > maxPrice) return false;
        if (!stringMatches(keywords, String.join(" ", document.keywords))) return false;
        if (!stringMatches(authors, String.join(" ", document.authors))) return false;

        if (documentType != null && !documentType.equals("")
                && !documentType.toLowerCase().equals(document.getDocType().toLowerCase()))
            return false;

        if (publicationYear != null) {
            if (document.getDocType().toLowerCase().equals("book")) {
                if (((Book) document).year != publicationYear) return false;
            } else if (document.getDocType().toLowerCase().equals("journalarticle")) {
                if (!((JournalArticle) document).publicationDate.toLowerCase().contains(("" + publicationYear).toLowerCase()))
                    return false;
            } else return false;
        }
        if (publicationMonth != null) {
            if (document.getDocType().toLowerCase().equals("journalarticle")) {
                if (!((JournalArticle) document).publicationDate.toLowerCase().contains(("" + publicationYear).toLowerCase()))
                    return false;
            } else return false;
        }

        if (journalName != null) {
            if (document.getDocType().toLowerCase().equals("journalarticle")) {
                if (!((JournalArticle) document).journalName.toLowerCase().contains((journalName).toLowerCase()))
                    return false;
            } else return false;
        }

        if (documentType != null) {
            if (!documentType.equals("") && !documentType.toLowerCase().equals(document.getDocType().toLowerCase()))
                return false;
        }

        if (edition != null) {
            if (document.getDocType().toLowerCase().equals("book")) {
                if (((Book) document).edition.toLowerCase().contains(edition.toLowerCase())) return false;
            } else return false;
        }

        if (editor != null) {
            if (document.getDocType().toLowerCase().equals("journalartiacle")) {
                if (((JournalArticle) document).editor.toLowerCase().contains(editor.toLowerCase())) return false;
            }
            return false;
        }

        if (isBestseller != null) {
            if (document.getDocType().toLowerCase().equals("book")) {
                if (((Book) document).isBestseller != isBestseller) return false;
            }
        }

        if (isAvailable != null) {
            if (document.getDocType().toLowerCase().equals("book")) {
                if (document.getNumberOfAvailableCopies() <= 1) return false;
            } else if (document.getNumberOfAvailableCopies() < 1) return false;
        }
        return true;
    }

}
