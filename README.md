# link-not-followed-by-arachni
Small webapp with 2 "list" pages, each with a link to an "edit" page. The links from listN to editN are not followed by Arachni, despite the audit-exclude-vector options.

It's a webapp very similar to the one on the "master" branch : I just duplicated "list" and "edit" to have 2 lists and 2 edits.

The webapp has 5 pages : 
- index.html : the welcome page, with 2 links to list1 and list2
- users/list1.xhtml : It contains a link "new user 1" which redirects to /users/edit1.xhtml.
- users/edit1.xhtml : It contains only the text " THIS IS THE PAGE I WANT TO SCAN - 1"
- users/list2.xhtml : It contains a link "new user 2" which redirects to /users/edit2.xhtml.
- users/edit2.xhtml : It contains only the text " THIS IS THE PAGE I WANT TO SCAN - 2"

I run Arachni with this command line : (my hostname is david-virtualbox)
```
( ~/arachni-2.0dev-1.0dev/bin/arachni http://david-virtualbox:8080/ --scope-dom-depth-limit=10 --scope-extend-paths="/home/david/PROJECTS/link-not-followed-by-arachni/arachni-paths.txt" --audit-links --audit-forms --audit-ui-inputs --audit-ui-forms --checks='sql_*,http_*,os_*,xss_*,insecure_*,allowed_methods,csrf,code_injection,directory_listing,emails,form_upload,session_fixation,xpath_injection' --platforms='linux,pgsql,oracle,tomcat,java,jsf' --report-save-path=pentest.afr --audit-exclude-vector=j_idt --audit-exclude-vector=javax.faces.ViewState  && ~/arachni-2.0dev-1.0dev/bin/arachni_reporter pentest.afr --reporter=html:outfile=pentest.html.zip ) > logs.txt
```

This command line works correctly on the "master" branch (= only 1 list and 1 edit pages), Arachni sees the "edit" page without problem (thanks to your audit-exclude-vector option).
But on this branch, the "edit" pages are not seen : 
edit1 and edit2 are not in the report, list1 has a 500 status code and list2 has 200. 

The webapp logs : 
```
GRAVE: Servlet.service() for servlet [faces] in context with path [] threw exception [viewId:/users/list1.xhtml - La vue «/users/list1.xhtml» n’a pas pu être restaurée.] with root cause
javax.faces.application.ViewExpiredException: viewId:/users/list1.xhtml - La vue «/users/list1.xhtml» n’a pas pu être restaurée.
```
La vue «/users/list1.xhtml» n’a pas pu être restaurée = View «/users/list1.xhtml» couldn't be restored.

Which explains the "500" status code. But why could the view not be restored ? Is the ViewState still invalidated by Arachni, despite the audit-exclude-vector ? How ?

I commited the file logs.txt containings the arachni logs, and the .afr and .html.zip reports, so you can have the exact informations.


## How to run

### With already created war file
I commited the war of the webapp (target/link-not-followed-by-arachni-1.0.0-SNAPSHOT.war), so you just have to use it in an already installed tomcat (if you have one).

### From the code
In the root directory, run : mvn clean tomcat7:run  (you need maven). The webapp will be accessible at : http://localhost:8080/index.xhtml

