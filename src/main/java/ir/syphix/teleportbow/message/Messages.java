package ir.syphix.teleportbow.message;

import ir.syphix.teleportbow.file.FileManager;

public class Messages {

    public static String PREFIX;
    public static String NEED_PERMISSION;
    public static String ALREADY_HAS;
    public static String GIVE_ITEMS;
    public static String MISSING_ARROW;
    public static String RELOAD;

    public Messages() {
        PREFIX = getMessage("prefix");
        NEED_PERMISSION = getMessage("need_permission");
        ALREADY_HAS = getMessage("already_has");
        GIVE_ITEMS = getMessage("give_items");
        MISSING_ARROW = getMessage("missing_arrow");
        RELOAD = getMessage("reload");
    }

    private String getMessage(String path) {
        return FileManager.MessagesFile.get().getString(path);
    }
}
