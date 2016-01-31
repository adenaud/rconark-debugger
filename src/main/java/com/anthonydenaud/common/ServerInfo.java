package com.anthonydenaud.common;

public class ServerInfo {
    private String name;
    private String map;
    private String folder;
    private String game;
    private short id;
    private int players;
    private int maxPlayers;
    private int bots;
    private char serverType;
    private char environment;
    private int visibility;
    private int vac;

    public ServerInfo() {
    }

    public ServerInfo(String name, String map, String folder, String game, short id, int players, int maxPlayers, int bots, char serverType, char environment, int visibility, int vac) {
        this.name = name;
        this.map = map;
        this.folder = folder;
        this.game = game;
        this.id = id;
        this.players = players;
        this.maxPlayers = maxPlayers;
        this.bots = bots;
        this.serverType = serverType;
        this.environment = environment;
        this.visibility = visibility;
        this.vac = vac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getBots() {
        return bots;
    }

    public void setBots(int bots) {
        this.bots = bots;
    }

    public char getServerType() {
        return serverType;
    }

    public void setServerType(char serverType) {
        this.serverType = serverType;
    }

    public char getEnvironment() {
        return environment;
    }

    public void setEnvironment(char environment) {
        this.environment = environment;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getVac() {
        return vac;
    }

    public void setVac(int vac) {
        this.vac = vac;
    }
}
