package org.creatorslane.glagchurch;

public class urlHelper {
    String BasePath = "http://workshop.creatorslane.org/Wesley/Books/";
    public String ToBook(String BookName, String Language,String format)
    {
        String temp=BasePath + BookName + "/" + Language + "/book."+format;
        String tempx=temp.replace(' ','_');
        return tempx;
    }

    public String ToCoverArt(String BookName, String Language)
    {
       String temp= BasePath + BookName + "/" + Language + "/.cover.png";
        String tempx=temp.replace(' ','_');
        return tempx;
    }

 /*   public String ToGetBooks()
    {
        return BasePath + "dir.php";
    }
    */
}
