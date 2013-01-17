package mygame.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Static convenience methods for common web-related tasks.
*/
public class EmailChecker {

public boolean checkEmail(String _in){
Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
Matcher m = p.matcher(_in);
boolean matchFound = m.matches();

if(matchFound)
{
return true;
}

else
{
    return false;
}
    }

}