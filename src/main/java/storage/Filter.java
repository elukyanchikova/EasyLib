package storage;

import documents.Book;
import documents.Document;
import documents.JournalArticle;
import java.util.ArrayList;

public class Filter {

    public String title;
    public String documentType;
    public ArrayList<String> authors = new ArrayList<>(); //TODO more partition
    public ArrayList<String> keywords = new ArrayList<>();  //TODO more partition
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

    //TODO: have a copy, bestSell, edition
    //TODO: publication Month for journal

    public boolean filter(Document document) {
        if (title != null)
            if (!document.title.toLowerCase().contains(title.toLowerCase())) return false;
        if (minPrice != null)
            if (document.price < minPrice) return false;
        if (maxPrice != null)
            if (document.price < maxPrice) return false;
        for (String keyword : keywords) {
            boolean flag = false;
            for (String docKeyword : document.keywords) {
                if (docKeyword.toLowerCase().contains(keyword.toLowerCase()))
                    flag = true;
            }
            if (!flag) return false;
        }
        for (String author : authors) {
            boolean flag = false;
            for (String docAuthor : document.authors) {
                if (docAuthor.toLowerCase().contains(author.toLowerCase()))
                    flag = true;
            }
            if (!flag) return false;
        }

        if (documentType != null) {
           if  (!documentType.equals("") && !documentType.toLowerCase().equals(document.getDocType().toLowerCase()))
                return false;
            }
        if (publicationYear != null) {
            if (document.getDocType().toLowerCase().equals("book")) {
                if (((Book) document).year != publicationYear) return false;
            } else if (document.getDocType().toLowerCase().equals("journalartiacle")) {
                if (!((JournalArticle) document).publicationDate.toLowerCase().contains(("" + publicationYear).toLowerCase()))
                    return false;
            } else return false;
        }
        if (publicationMonth != null) {
            if (document.getDocType().toLowerCase().equals("journalartiacle")) {
                if (!((JournalArticle) document).publicationDate.toLowerCase().contains(("" + publicationYear).toLowerCase()))
                    return false;
            } else return false;
        }

        if (journalName != null) {
            if (document.getDocType().toLowerCase().equals("journalartiacle")) {
                if (!((JournalArticle) document).journalName.toLowerCase().contains((journalName).toLowerCase()))
                    return false;
            } else return false;
        }


        if(documentType != null){
           if(!documentType.equals("") && !documentType.toLowerCase().equals(document.getDocType().toLowerCase())) return false;
        }
        if(publicationYear != null){
            if(document.getDocType().toLowerCase().equals("book")) {
                if(((Book)document).year != publicationYear)  return false;
            }else if(document.getDocType().toLowerCase().equals("journalartiacle")){
                if(!((JournalArticle)document).publicationDate.toLowerCase().contains(("" +publicationYear).toLowerCase()))  return false;
            }else return false;
        }
        if(publicationMonth != null){
            if(document.getDocType().toLowerCase().equals("journalartiacle")){
                if(!((JournalArticle)document).publicationDate.toLowerCase().contains(("" +publicationYear).toLowerCase()))  return false;
            }else return false;
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
                return false;
            }

            if (isAvailable != null) {
                if (document.getDocType().toLowerCase().equals("book")) {
                    if (document.getNumberOfAvailableCopies() <= 1) return false;
                } else if (document.getNumberOfAvailableCopies() < 1) return false;
            }
            return true;
        }

    }
