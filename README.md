# link-not-followed-by-arachni
Small webapp with a link not followed by Arachni.

The webapp has 2 pages : 
- /users/list.xhtml : It contains a link "new user" which redirects to /users/edit.xhtml.
- /users/edit.xhtml : It contains only the text " THIS IS THE PAGE I WANT TO SCAN"

The page /users/list.xhtml is listed in the file for 'scope-extend-paths'. Arachni see it.

The page /users/edit.xhtml is not listed in the file for 'scope-extend-paths', but I expect Arachni to see the link on /users/list and to follow it (I can't list the 'edit' page directly in scope-extend-paths because, in my real webapp, I need to transfer some informations from 'list' to 'edit'). But it seems Arachni doesn't. 


