u must activate  EMail authentication on the
data base and add jason.javascript file provided
by data base to app rep
also app must use the package used to 
create the data base
or implement this in rules
rules{
"read":"auth!=null",
"write":"auth!=null",
}
