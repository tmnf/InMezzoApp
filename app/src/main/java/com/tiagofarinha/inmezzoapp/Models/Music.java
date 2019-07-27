package com.tiagofarinha.inmezzoapp.Models;

import androidx.annotation.Nullable;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Music implements Adaptable {

    private String name, artist;

    public Music(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public Music() {
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Music aux = (Music) obj;

        return aux.getArtist().equals(artist) && aux.getName().equals(name);
    }
}
