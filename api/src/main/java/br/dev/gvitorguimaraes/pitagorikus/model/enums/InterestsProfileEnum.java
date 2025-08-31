package br.dev.gvitorguimaraes.pitagorikus.model.enums;

public enum InterestsProfileEnum {
    MATHEMATICS			("MATH", "Mathematics"),
    PHYSICS				("PHYS", "Physics"),
    CHEMISTRY			("CHEM", "Chemistry"),
    BIOLOGY				("BIOL", "Biology"),
    COMPUTER_SCIENCE	("CS", "Computer Science"),
    LITERATURE			("LIT", "Literature"),
    HISTORY				("HIST", "History"),
    GEOGRAPHY			("GEO", "Geography"),
    PHILOSOPHY			("PHIL", "Philosophy"),
    ECONOMICS			("ECON", "Economics"),
    PSYCHOLOGY			("PSY", "Psychology"),
    ART					("ART", "Art"),
    MUSIC				("MUS", "Music");

    private final String code;
    private final String description;

    InterestsProfileEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static InterestsProfileEnum fromCode(String code) {
        for (InterestsProfileEnum interest : values()) {
            if (interest.code.equalsIgnoreCase(code)) {
                return interest;
            }
        }
        throw new IllegalArgumentException("Invalid StudyInterest code: " + code);
    }
}
