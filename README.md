# link-not-followed-by-arachni

## Problem description

Small webapp with a link not followed by Arachni.

The webapp has 2 pages : 
- /users/list.xhtml : It contains a link "new user" which redirects to /users/edit.xhtml.
- /users/edit.xhtml : It contains only the text " THIS IS THE PAGE I WANT TO SCAN"

The page /users/list.xhtml is listed in the file for 'scope-extend-paths'. Arachni see it.

The page /users/edit.xhtml is not listed in the file for 'scope-extend-paths', but I expect Arachni to see the link on /users/list and to follow it (I can't list the 'edit' page directly in scope-extend-paths because, in my real webapp, I need to transfer some informations from 'list' to 'edit'). But it seems Arachni doesn't. 

I commited the report so you can see users/edit.xhtml is not listed in the sitemap.


## How to run

### With already created war file
I commited the war of the webapp (target/link-not-followed-by-arachni-1.0.0-SNAPSHOT.war), so you just have to use it in an already installed tomcat (if you have one).

### From the code
In the root directory, run : mvn clean tomcat7:run  (you need maven). The webapp will be accessible at : http://localhost:8080/users/list.xhtml


## Solution

Turns out that it has to do with Arachni auditing the inputs of the hidden form wrapped around that link, which are used to pass viewstate tokens.
That audit invalidatd the viewstate so when the browsers click the link the application returns an error instead of redirecting to the edit page.

You can skip auditing those input vectors with:
```
--audit-exclude-vector=javax.faces.ViewState --audit-exclude-vector=j_idt
```

The values to these options are treated as regular expressions, so any input vector that includes them will be ignored (one of them was j_idt8, not sure if the integer changes so I used j_idt as a catch all).


