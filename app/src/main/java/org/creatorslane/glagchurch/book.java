package org.creatorslane.glagchurch;

public class book {

    String name;
    String lang;
    String storage;
    String cover;
    public String getname()
    {
        return this.name;
    }
    public String getlang()
    {
        return this.lang;
    }
    public String getCover()
    {
        return this.cover;
    }
    public String getStorage()
    {
        return this.storage;
    }

    public book(String name, String lang, String storage, String cover)
    {
        this.name=name;
        this.lang=lang;
        this.storage=storage;
        this.cover=cover;
    }

}
