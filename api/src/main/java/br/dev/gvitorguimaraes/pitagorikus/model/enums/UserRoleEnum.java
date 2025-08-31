package br.dev.gvitorguimaraes.pitagorikus.model.enums;

public enum UserRoleEnum
{
    ADMIN      ("A"),
    USER       ("U");

    private final String code;

    UserRoleEnum(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public static UserRoleEnum fromCode(String code)
    {
        for (UserRoleEnum role : UserRoleEnum.values())
        {
            if (role.getCode().equals(code))
            {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: "+code);
    }
    
    public boolean isAdmin()
    {
    	return this.equals(ADMIN);
    }
    
    public boolean isUser()
    {
    	return this.equals(USER);
    }
}
