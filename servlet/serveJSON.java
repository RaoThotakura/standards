
package client;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStream; 
import java.io.IOException; 
import java.io.OutputStream;
import java.lang.String;
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonException;

/**
* servlet to serve a JSON file
* @author rao s. thotakura: 05/20/2016 
 */
public class ServeJSON extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

        boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        JSONObject json = new JSONObject();
        JSONObject jelement = new JSONObject();
        try {
            if (request. getParameter("task").equals("disp_client"))
                JSONArray jarray = new JSONArray();

                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=346&cln_name=ABC Global Conversion Services");
                jelement.put("addprojecturl","http://qweoctdweb1:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cIn_id=346&cln _name=ABC Global Conversion Services");
                jelement.put("expandprojecturl", "http://localhost:8080/examples/ServeJSON?pid-map&sel_snl=dis1&guid=4773 :sthotakura: 20160512.074544&task=disp_project&cin_id=346&cIn _name=ABC Global Conversion Services");
                jelement.put("clientid", "346");
                jelement.put("clientname", "ABC Global Conversion Services");
                jarray.add(jelement);
                jelement.put("seturl","http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=347&cln name=abc");
                jelement.put("addprojecturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis|&guid=4773:sthotakura: 20160512.074544&task=disp_map_task&cIn_id=347&cln_name=abc");
                jelement.put("expandprojecturl", "http://localhost:8080/examples/ServeJSON?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_project&cin_ id=346&cln_name=abc");
                jelement.put("clientid", "587"); 
                jelement.put("clientname", "Abc");
                jarray.add(jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=348&cln _name-incyte");
                jelement.put("addprojecturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura: 20160512.074544&task-disp_map _task&cln_id=348&cIn _name=incyte");
                jelement.put("expandprojecturl", "http://localhost:8080/examples/ServeJSON?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task-disp_project&cin_id=346&cIn_ name-incyte");
                jelement.put("clientid", "609");
                jelement.put("clientname", "ARDS 2.2 Dev");
                jarray.add(jelement);
                json.put("CLIENTS" jarray);

            } else if (request.getParameter("task").equals("disp_project")) {
                JSONArray jprjarray = new JSONArray();
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=346&cln_name=ARDS 2.2 Dev&pri_id=847&prj_name=AutoDCD Dev 2.0");
                jelement.put("addstudyurl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new_task=add_study&cln_id=609&cln_name=ARDS 2.2Dev&pri_id=847&prj_name=AutoDCD Dev 2.0");
                jelement.put("expandstudyurl", "http://localhost:8080/examples/ServeJSON?pid=map&sel_sn1=dis1&guid=4773:sthotakura: 20160512.074544&task=disp_study&cin_id=609&cIn_name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCD Dev");
                jelement.put("projectid", "847");
                jelement.put("projectname", "AutoDCD Dev 2.0");
                jprjarray.add(jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=587&cln_name=ARDS 2.2 Dev&pri_id=847&prj_name=AutoDCD Dev 2.1");
                jelement.put("addstudyurl", "http://qweoctdweb1:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura: 20160512.074544&task=disp_new&new_ task=add _study&cln_id=609&eln_name=ARDS 2.2Dev&pri_id=847&prj_name=AutoDCD Dev 2.1");
                jelement.put("expandstudyurl", "http://localhost:8080/examples/ServeJSON?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_study&cln_id=609&cln name=ARDS 2.2 Dev&pri_id=847&prj_name=AutoDCD Dev2.1");
                jelement.put("projectid", "848");
                jelement.put("projectname", "AutoDCD Dev 2.1");
                jprjarray.add(jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=disl&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCD Dev 2.1.2");
                jelement.put("addstudyur!", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new_task=add_ study&cln_id=609&cln_name=ARDS 2.2Dev&prj_id=847&prj_name=AutoDCD Dev 2.1.2");
                jelement.put("expandstudyurl", "http://localhost:8080/examples/ServeJSON?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_study&cln_id=609&cln _name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCD Dev");
                jelement.put("projectid", "849");
                jelement.put("projectname", "AutoDCD Dev 2.1.2");
                Jprjarray. add (element);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln _name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCD Dev 2.1.3");
                jelement.put("addstudyurl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid-map&sel_sn1=dis1&guid=4773 :sthotakura:20160512.074544&task=disp_new&new _task=add_ study&cln_id=609&cln_ name=ARDS 2.2Dev&prj_id=847&prj_name=AutoDCD Dev 2.1.3");
                jelement.put("expandstudyurl", "http://localhost:8080/examples/ServeJSON?pid=map&sel_snl=dis1&guid=4773:sthotakura: 20160512.074544& task=disp_study&ln_id=609&cln_name=ARDS 2.2 Dev&pri_id=847&pri_name=AutoDCD Dev2.1.3");
                jelement. put("projectid", "850"); 
                jelement.put("projectname", "AutoDCD Dev 2.1.3");
                jprjarray.add(jelement);
                jelement.put("seturl", "http://qwectdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=disl&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=628&cln_name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCD Dev 2.1.4");
                jelement.put("addstudyurl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid-map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new_task=add_study&cln_id=609&cln_name=ARDS 2.2Dev&prj_id=847&prj_name=AutoDCD Dev 2.1.4");
                jelement.put("expandstudyurl", "http://localhost:8080/examples/ServeJSON?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_study&cln_id=609&cln_name=ARDS 2.2 Dev&pri_id=847&prj_name=AutoDCD Dev2.1.4"):
                jelement.put("projectid", "860");
                jelement.put("projectname", "AutoDCD Dev 2.1.4");
                jprjarray.add (jelement);
                json.put("PROJECTS" jprjarray);
            } else if (request.getParameter("task").equals("disp_study")) {
                JSONArray jstdyarray = new JSONArray();
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&prj id=849&prj name=AutoDCDDev&study_id=1308&study_name=2014-10-Study-A");
                jelement.put("addlistur!", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new_task=add_ list&cln_ id=609&cIn_name=ARDS 2.2Dev&pri_id=847&pri_name=AutoDCD Dev&study _id=1308&study_ name=2014-10-Study-A");
                jelement.put("expandlisturl", "http://qweoctdweb1:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura: 20160512.074544&task=disp_list&cln_id=609&cln_name=ARDS 2.2 Dev&pri_id=847&prj_name=AutoDCDDev&study_id=1308&study_name=2014-10-Study-A");
                jelement. put("studyid", "1308");
                jelement.put("studyname", "2014-10-Study-A");
                jstdyarray.add(jelement);
                jelement.put("seturl", "http://qweoctdweb1:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&pri_id=849&prj_name=AutoDCDDev&study_id=1309&study_name=SDTM_V32_DEV");
                jelement.put("addlistur!", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new_task=add _list&cln_id=609&eln_name=ARDS 2.2Dev&pri_id=847&prj_name=AutoDCD Dev&study_id=1309&study_name=SDTM_V32_DEV");
                jelement.put("expandlisturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura: 20160512.074544&task=disp_list&cin_id=609&cIn_name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCDDev&study_id=1309&study_name=SDTM_V32_DEV");
                jelement.put("studyid"," 1309"); 
                jelement.put("studyname", "SDTM_V32_DEV");
                jstdyarray.add(jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=628&cln_name=ARDS 2.2 Dev&pri_id=848&pri_name=AutoDCDDev&study_ id=1310&study_name=SDTM_V313_DEV");
                jelement.put("addlisturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773 :sthotakura:20160512.074544&task=disp_new&new_task=add_list&cln_id=609&cln_name=ARDS 2.2Dev&pri_id=847&pri_name=AutoDCD Dev&study _id=1310&study_name=SDTM_V313_DEV");
                jelement.put("expandlisturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773:sthotakura:20160512.074544&task-disp_list&cln_id=609&cln_name=ARDS 2.2 Dev&pri_id=847&pri_name=AutoDCD Dev&study_id=1310&study_name=SDTM_V313_DEV");
                jelement.put("studyid","1310");
                jelement. put("studyname","SDTM_V313 _DEV");
                jstdyarray.add(jelement);
                json.put("STUDIES", jstdyarray);
            } else if (request.getParameter("task").equals("disp_list")) { 
                JSONArray jlistarray = new JSONArray ();
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&prj_id=849&prj_name=AutoDCDDev&study_id=1308&study_name=TEST32_SCHEMA&list_id=1533&sponsor=//QWEOCTDWEB 1/share/autodd/specs/dis 1_1312/Mapping_Specifications_CKM248369_2015-03-02T21-35-40.xIs*");
                jelement.put("addjoburl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new_task=add_job&cln_id=609&cln_name=ARDS 2.2Dev&prj_id=847&prj_name=AutoDCDDev&study_id=1312&study_name-TEST32_SCHEMA&list id=1533&sponsor=//QWEOCTDWEB 1/share/autodcd/specs/disl_1312/Mapping_ Specifications_CKM248369_2015-03-02T21-35-40.xls");
                jelement.put("expandjoburl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_job&cln_id=609&cln_name=ARDS 2.2 Dev&pri_id=847&prj_name=AutoDCDDev&study_ id=1312&study_name=TEST32_SCHEMA&list id= 1533&sponsor=//QWEOCTDWEB 1/share/autodd/specs/dis I_1312/Mapping_ Specifications_CKM248369_2015-03-02T21-35-40.xIs");
                jelement.put("listid","1533");
                jelement.put("listname","//QWEOCTDWEB 1/share/autodcd/specs/dis 1_1312/Mapping_Specifications_CKM248369_2015-03-02T21-35-40.xls");
                jlistarray.add(jelement);
                jelement.put("setur!", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=-map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&pri_id=849& prj_name=AutoDCDDev&study_ id=1308&study_name-TEST32_SCHEMA&list_id=1494&sponsor=//QWEOCTDWEB 1/share/autodd/specs/disI_ 1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xls");
                jelement.put("addjoburl", "http://qweoctdweb1:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new_task=add_job&cln_id=609&cln_name=ARDS 2.2Dev&prj_id=847&prj_name=AutoDCDDev&study_ id=1312&study_name=TEST32_SCHEMA&list_id=1494&sponsor=//QWEOCTDWEB1/share/autodcd/specs/dis 1_1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xls");
                jelement.put("expandjoburl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_job&cln_id=609&cln_name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCDDev&study_id=1312&study_ name=TEST32_SCHEMA&list_id=1494&sponsor=//QWEOCTDWEB 1/share/autodd/specs/dis1_1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xls");
                jelement.put("listid","1494");
                jelement.put("listname","*//QWEOCTDWEB 1/share/autodcd/specs/dis1_1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xls");
                jlistarray.add(jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCDDev&study_ id=1312&study_ name=TEST32_SCHEMA&list id=1494&sponsor=//QWEOCTDWEB 1/share/autodcd/specs/dis1_1312/poolde(2014A1-AutoDCD _DCS_Final_v32-spec1.1.xls");
                jelement.put("addjoburl", "http://qweoctdweb1:8088/cgi/map_spec.pl?pid-map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new task=add _job&cln_id=609&cln_name=ARDS 2.2Dev&prj_id=847&prj_name=AutoDCDDev&study_ id=1312&study_ name-TEST32_SCHEMA&list id=1494&sponsor=|/QWEOCTDWEB 1/share/autodd/specs/dis 1_1312/pooldef2014A1-AutoDCD_DCS_Final _v32-spec1.1 x/s");
                jelement.put("expandjoburl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773:sthotakura: 20160512.074544& task-disp_job&cIn_id=609&cln_name=ARDS 2.2 Dev&pri_id=847&prj_name=AutoDCDDev&study_ id=1312&study _name-TEST32_ SCHEMA&list id= 1494&sponsor=//QWEOCTDWEB 1/share/autodd/specs/dis 1_ 1312/pooldef2014A1-AutoDCD_DCS _Final_v32-spec1.1.xIs");
                jelement.put("listid","1500");
                jelement.put("listname","//QWEOCTDWEB 1_1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.2.xls");
                jlistarray.add (jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cIn_id=609&cln _name=ARDS 2.2 Dev&pri_id=847&prj_name=AutoDCDDev&study_ id=1312&study_ name=TEST32_SCHEMA&list_id=1494&sponsor=//QWEOCTDWEB 1/share/autodcd/specs/dis 1_1312/pooldef2014A1-AutoDCD_DCS _Final _v32-spec1.1.xls");
                jelement.put("addjoburl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid-map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&task=disp_new&new _task=add job&cln_ id=609&cln_name=ARDS 2.2Dev&prj_id=847&рrj_name=AutoDCDDev&study_id=1312&study_name=TEST32_ SCHEMA&list_id=1494&sponsor=//QWEOCTDWEB 1/share/autodcd/specs/dis1_1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xls");
                jelement.put("expandjoburl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773:sthotakura:20160512.074544&task=disp_job&cln_id=609&cln_name=ARDS 2.2 Dev&prj_id=847&prj_name=AutoDCD1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xlsDev&study_id=1312&study_name=TEST32_SCHEMA&list_id=1494&sponsor=//QWEOCTDWEB1/share/autodcd/spees/dis1_AutoDCD_DCS_Final_v32-spec1.3.xls");
                jelement.put("listid","1600");
                jelement.put("listname","//QWEOCTDWEB 1/share/autodcd/specs/dis1_1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.2.xls");
                jlistarray.add(jelement);
                json.put("LISTS" jlistarray);
            } else if (request.getParameter("task").equals("disp_job")) {
                JSONArray jjobarray = new JSONArray();
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=disl&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&pri_id=849&prj_name=AutoDCDDev&study_ id=1308&study _name=TEST32_ SCHEMA& list id=1533&sponsor=//QWEOCTD WEB 1/share/autodd/specs/dis 1_ 1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xls&job_id=17083&job_name=SDTM LDC; 20160415.135144;Completed with 1 domains failed; sid=1312;lid=1494;dmn=ALL");
                jelement.put("showjobstaturl", "http://qweoctdweb1:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&no_dispform=1&task=sel_stat&sel_sn2=17083");
                jelement.put("jobstatusdesc","17083 - SDTM LDC; 20160415.135144; Completed with 1 domains failed; sid=1312;lid=1494;dmn=ALL");
                jjobarray.add(jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=disl&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&prj_id=849&prj_name=AutoDCDDev&study_ id=1308&study_ name-TEST32_SCHEMA&list_ id= 1533&sponsor=//QWEOCTDWEB 1/share/autodd/specs/dis 1_ 1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xls&job_id=17082&job_name=SDTM LDC; 20160415.134618;Completed with 43 domains failed; sid=1312;lid=1494;dmn=ALL");
                jelement.put("showjobstaturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&no_dispform=1&task=sel _stat&sel_sn2=17082");
                jelement.put("jobstatusdes", "17082 - SDTM LDC; 20160415.134618; Completed with43 domains failed; sid=1312;lid=1494;dmn=ALL");
                jjobarray.add(jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773:sthotakura:20160512.074544&task=disp_map_task&cln_id=609&cln _name=ARDS 2.2 Dev&prj_id=849&pri_name=AutoDCDDev&study_id=1308&study_name=TEST32_ SCHEMA&list_id=1533&sponsor=//QWEOCTDWEB1/share/autodcd/specs/dis 1_ 1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xIs&job_ id=17081&job_ name=SDTM LDC; 20160415.133507;Completed with 16 domains failed; sid=1312;lid=1494;dmn=ALL");
                jelement.put("showjobstaturl", "http://qweoctdweb1:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544&no _dispform= 1&task=sel_stat&sel_sn2=17081");
                jelement.put("jobstatusdesc","17081 - SDTM LDC; 20160415.133507; Completed with16 domains failed; sid=1312;lid=1494;dmn=ALL");
                jjobarray. add(jelement);
                jelement.put("seturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_snl=dis1&guid=4773:sthotakura:20160512.074544& task-disp_map_task&cln_id=609&cln_name=ARDS 2.2 Dev&prj_id=849&prj name=AutoDCDDev&study_ id=1308&study_name=TEST32_SCHEMA&list id=1533&sponsor=//QWEOCTDWEB1/share/autodcd/specs/dis1_1312/pooldef2014A1-AutoDCD_DCS_Final_v32-spec1.1.xls&job_id=17078&job_name=SDTM LDC; 20160415.130822;Completed with 1 domains failed; sid=1312;lid=1494;dmn=ALL");
                jelement.put("showjobstaturl", "http://qweoctdwebl:8088/cgi/map_spec.pl?pid=map&sel_sn1=dis1&guid=4773:sthotakura: 20160512.074544&no _dispform=1&task=sel_stat&sel_sn2=17078");
                jelement.put("jobstatusdesc","17078 - SDTM LDC; 20160415.130822; Completed with 1domains failed; sid=1312;lid=1494;dmn=ALL");
                jjobarray.add(jelement);
                json.put("JOBS",jjobarray);
            } else if (request.getParameter("task").equals("disp_job_status")) {
                JSONArray jstatusarray = new JSONArray();
                jelement.put("statId","49007");
                jelement.put("jobld", "17374");
                jelement.put("domain", "CM");
                jelement.put("startTime", "20160512.072954"); 
                jelement.put("endTime", "20160512.072954"); 
                jelement.put("action", "*");
                jelement.put("status", "Running"); 
                jelement.put("remark", "4/45 (CM)");
                jelement.put("outPath", "*");
                jelement.put("outMessage", "*");
                jelement.put("numRows", "");
                jelement.put("dbUser", "MAP _ADMIN2");
                jelement.put("osUser", "SYSTEM");
                jelement.put("app User", "sthotakura");
                jstatusarray.add(jelement);
                jelement.put("statId", "49006");
                jelement.put("jobld","17374");
                jelement.put("domain", "CD");
                jelement.put("startTime", "20 160512.072939"); 
                jelement.put("end Time", "20160512.072954"); 
                jelement.put("action",'');
                jelement.put("status", "OK");
                jelement.put("remark", "3/45 CD): done in 15 seconds with AUTO_DCD inputs(CD,3.2,null, TEST32_SCHEMA, TEST32_SCHEMA,MAP_ ADMIN2.sp_specs, 1,1494,1)");
                jelement.put("outPath", "*");
                jelement.put("outMessage", '');
                jelement.put("numRows", "629");
                jelement.put("dbUser", "MAP_ADMIN2");
                jelement.put("osUser", "SYSTEM");
                jelement.put("appUser", "sthotakura");
                jstatusarray.add(jelement);
                json.put("STATUS",jstatusarray);
            } else if (request.getParameter ("task").equals("add_project")) {
                JSONArray addprjarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel","LOG _LEVEL: 3");
                jelement.put("msglevel","MSG_LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("jobid", "Job ID: 17374"); 
                jelement.put("jobargs", "Job Args: sid=1312;lid=1494;dmn=ALL"); 
                jelement.put("appuser", "App User: sthotakura");
                jelement.put("outnum", "Out Num: 0");
                jelement.put("outmsg", "Out Msg: OK");
                jelement.put("success", "PL/SQL procedure successfully completed");
                jelement.put("info 1", "INFO(sp_addajob_sp) 1 - check inputs..."); 
                jelement.put("info2", "TNFO(sp_addajob_sp) 2 - check objects...");
                jelement.put("info3", "INFO(sp _addajob_sp) 3 - compose job _args..."); 
                jelement.put("info4", "INFO(sp_addajob_sp) 4 - add a new job..."); 
                jelement.put("infos","INFO(sp_addajob_sp) 5 - commit all changes...");
                addprjarray.add(jelement);
                json.put("LOGS",addprjarray);
            } else if (request.getParameter ("task").equals("add_study")) {
                JSONArray addstdyarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel", "LOG_LEVEL: 3"); 
                jelement.put("msglevel", "MSG_LEVEL: 1");
                jelement.put("gid","G_JID: 0");
                jelement.put("jobid","Job ID: 17374"); 
                jelement.put("jobargs", "Job Args: sid=1312;lid=1494;dmn=ALL"); 
                jelement.put("appuser", "App User: sthotakura");
                jelement.put("outnum", "Out Num: 0"); 
                jelement.put("outmsg", "Out Msg: OK");
                jelement.put("success", "PL/SQL procedure successfully completed"); 
                jelement.put("info 1", "INFO(sp_addajob_sp) 1 - check inputs..."); 
                jelement.put("info2", "INFO(sp_addajob_sp) 2 - check objects..."); 
                jelement.put("info3", "INFO(sp_addajob_sp) 3 - compose job _args..."); 
                jelement.put("info4","TNFO(sp_addajob_sp) 4 - add a new job...");
                jelement.put("info5", "INFO(sp_addajob_sp) 5 - commit all changes..."); 
                addstdyarray.add(jelement);
                json.put("LOGS", addstdyarray);
            } else if (request.getParameter("task").equals("add_list")) {
                JSONArray addlstarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel", "LOG_LEVEL: 3"); 
                jelement.put("msglevel", "MSG_LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("jobid", "Job ID: 17374");
                jelement.put("jobargs", "Job Args: sid=1312;lid=1494;dmn=ALL"); 
                jelement.put("appuser", "App User: sthotakura");
                jelement.put("outnum", "Out Num: 0"); 
                jelement.put("outmsg", "Out Msg: OK");
                jelement.put("success", "PL/SQL procedure successfully completed"); 
                jelement.put("infol", "INFO(sp_addajob_sp) 1 - check inputs..."); 
                jelement.put("info2", "INFO(sp_addajob_sp) 2 - check objects..."); 
                jelement.put("info3", "INFO(sp_addajob_sp) 3 - compose job _args..."); 
                jelement.put("info4", "INFO(sp_addajob_sp) 4 - add a new job..."); 
                jelement.put("infoS", "INFO(sp_addajob_sp) 5 - commit all changes...");
                addlstarray.add(jelement);
                json.put("LOGS", addlstarray);
            } else if (request.getParameter("task").equals("add_job")) {
                JSONArray addjobarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel", "LOG _LEVEL: 3"); 
                jelement.put("msglevel", "MSG _LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("jobid", "Job ID: 17374"); 
                jelement.put("jobargs", "Job Args: sid=1312;lid=1494;dmn=ALL"); 
                jelement.put("appuser","App User: sthotakura");
                jelement.put("outnum", "Out Num: 0"); 
                jelement.put("outmsg", "Out Msg: OK");
                jelement.put("success", "PL/SQL procedure successfully completed");
                jelement.put("infol", "INFO(sp_addajob_sp) 1 - check inputs..."); 
                jelement.put("info2", "INFO(sp_addajob_sp) 2 - check objects..."); 
                jelement.put("info3", "INFO(sp_addajob_sp) 3 - compose job _args..."); 
                jelement.put("info4", "INFO(sp_addajob_sp) 4 - add a new job..."); 
                jelement.put("infos", "INFO(sp_addajob_sp) 5 - commit all changes...");
                addjobarray.add(jelement);
                json.put("LOGS", addjobarray);
            } else if (request.getParameter ("task").equals("run_a_job")) {
                JSONArray runjobarray = new JSONArray();
                jelement.put("parameter", "parameter - 17374:1494: ALL: null:null:null"); 
                jelement.put("loglevel", "LOG_LEVEL: 3"); 
                jelement.put("msglevel", "MSG_LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("jobid", "Job ID: 17374"); 
                jelement.put("jobargs", "Job Args: sid=1312;lid=1494;dmn=ALL"); 
                jelement.put("appuser", "App User: sthotakura");
                jelement.put("outnum", "Out Num: 0");
                jelement.put("outmsg", "Out Msg: OK");
                jelement.put("success", "PL/SQL procedure successfully completed"); 
                jelement.put("infol","INFO(sp_schajob_sp) 1 - check inputs..."); 
                jelement.put("info2", "INFO(sp_schajob_sp) 2 - check objects..."); 
                jelement.put("info3","TNFO(sp_schajob_sp) 3 - lock the study..."); 
                jelement.put("info4", "INFO(sp_study_lock_sp) 0 - check inputs..."); 
                jelement.put("info5","INFO(sp_study_lock_sp) 1 - check study...");
                jelement.put("info6", "INFO(sp_study_lock_sp) 3 - lock study..."); 
                jelement.put("info7"," INFO(sp_schajob_sp) 4 - add a job..."); 
                jelement.put("info8", "INFO(sp_schajob_sp) 5 - build sql statement..."); 
                jelement.put("info9" "INFO(sp_schajob_sp) 7 - submit the job..."); 
                jelement.put("info 10","INFO(sp_schajob_sp) 8 - update the job...");
                runjobarray.add(jelement);
                json.put("LOGS",runjobarray);
            } else if (request.getParameter ("task").equals ("add_a_job")) {
                JSONArray runjobarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel" "LOG_LEVEL: 3");
                jelement.put("msglevel", "MSG_LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("jobid", "Job ID: 17374"); 
                jelement.put("jobargs", "Job Args: sid=1312;lid=1494;dmn=ALL");
                jelement.put("appuser", "App User: sthotakura");
                jelement.put("outnum", "Out Num: 0"); 
                jelement.put("outmsg", "Out Msg: OK");
                jelement.put("success", "PL/SQL procedure successfully completed"); 
                jelement.put("info 1", "INFO(sp_addajob_sp) 3 - compose job _args..."); 
                jelement.put("info2", "INFO(sp_schajob_sp) 2 - check objects...");
                jelement.put("info3", "INFO(sp_schajob_sp) 3 - lock the study..."); 
                jelement.put("info4", "INFO(sp_addajob_sp) 4 - add a new job..."); 
                jelement.put("info5", "INFO(sp_addajob_sp) 5 - commit all changes...");
                runjobarray.add (jelement);
                json.put("LOGS", runjobarray);
            } else if (request.getParameter("task").equals("run_renametable")) {
                JSONArray runjobarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel", "LOG_LEVEL: 3"); 
                jelement.put("msglevel", "MSG _LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("success", "PL/SQL procedure successfully completed");
                runjobarray.add(jelement);
                json.put("LOGS", runjobarray);
            } else if (request.getParameter ("task").equals("run_droptable")) {
                JSONArray runjobarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel", "LOG_LEVEL: 3");
                jelement.put("msglevel", "MSG_LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("success", "PL/SQL procedure successfully completed");
                runjobarray.add (jelement);
                json.put("LOGS", runjobarray);
            } else if (request.getParameter ("task").equals("run_cptable")) {
                JSONArray runjobarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel", "LOG _LEVEL: 3");
                jelement.put("msglevel", "MSG_LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("success", "PL/SQL procedure successfully completed");
                runjobarray.add(jelement);
                json.put("LOGS", runjobarray);
            } else if (request.getParameter ("task"). equals("add_a_dblink")) {
                JSONArray runjobarray = new JSONArray();
                jelement.put("parameter", "parameter - 1494:ALL:null:null:null:null");
                jelement.put("loglevel", "LOG_LEVEL: 3"); 
                jelement.put("msglevel", "MSG_LEVEL: 1");
                jelement.put("gid", "G_JID: 0");
                jelement.put("success", "PL/SQL procedure successfully completed"); 
                runjobarray.add(jelement);
                json.put("LOGS", runjobarray);
            } else if (request.getParameter("task").equals("set_a_client")) {
                jelement. put("client", "ARDS 2.2 Dev");
                json.put("RESULT" jelement);
            } else if (request.getParameter ("task").equals("set_a_project")) {
                jelement.put("project", "AutoDCD Dev 3"); 
                json.put("RESULT" jelement);
            } else if (request.getParameter("task").equals("set_a_study")) {
                jelement.put("study" "2016-99-Study-X");
                json.put("RESULT" jelement);
            } else if (request.getParameter("task").equals("display_logfiles")) {
                JSONArray logfilesarray = new JSONArray();
                jelement.put("jobld", "17374");
                jelement.put("logTitle","//orswb l/share/autodcd/joblogs/dis1/SVC_octapache_app/2016/05/12/");
                jelement.put("logText", "20160512.072922: SDTM LDC Completed with 2 domains");
                logfilesarray.add(jelement);
                jelement.put("jobld", "17374");
                jelement.put("logTitle", "//orswb/share/autodcd/joblogs/dis1/SVC_octapache _app/2016/05/12/");
                jelement.put("logText","20160415.135144: SDTM LDC Completed with 1 domains");
                logfilesarray.add(jelement);
                jelement.put("jobld", "17083");
                jelement.put("logTitle","//orswbl/share/autodd/joblogs/dis1/SVC _octapache _app/2016/05/12/");
                jelement.put("logText", "20160415.134618: SDTM LDC Completed with 43 domains");
                logfilesarray.add(jelement);
                json.put("LOGS", logfilesarray);
            } else if (request.getParameter("task").equals("expand_log")) {
                JSONArray logviewarray = new JSONArray();
                jelement.put("logTitle","//orswb l/share/autodcd/joblogs/dis1/SVC_octapache _app/2016/05/12/");
                jelement.put("logText","17374_TEST32_SCHEMA_AE.html");
                jelement.put("logType", "html");
                logviewarray.add(jelement);
                jelement.put("logTitle","//orswb/share/autodcd/joblogs/dis1/SVC_octapache_app/2016/05/12/");
                jelement.put("logText", "17374_TEST32 SCHEMA AE FINALS");
                jelement.put("log Type", "sq!");
                logviewarray.add(jelement);
                jelement.put("logTitle","//orswb/share/autodd/joblogs/dis1/SVC_octapache _app/2016/05/12/");
                jelement.put("logText", " 17374_TEST32_SCHEMA_APCO.html");
                jelement.put("logType", "htm!");
                logviewarray.add(jelement);
                jelement.put("log Title","//orswbl/share/autodcd/joblogs/dis/SVC_octapache_app/2016/05/12/");
                jelement.put("logText", "17374_TEST32_SCHEMA_AE_FINAL.sql");
                jelement.put("logType", "sql");
                logviewarray.add(jelement);
                json.put("LOGS", logviewarray);
            } else if (request.getParameter("task").equals("expand_logmesg")) {
                JSONArray logmesgarray = new JSONArray();
                jelement.put("sq|Script", "sql_script = SELECT SDTM.class _name,SDTM.domain_ name, SDTM.variable_ name, SDTM.variable_label, SDTM.variable_type, SDTM.variable_ length,
                SDTM.variable_permissibility, SDTM.variable_origin, SDTM.variable_role, SDTM.variable_position, SPEC.spec_id,UPPER(TRIM(SPEC.source _dataset)) AS SOURCE_DATASET, UPPER(TRIM(SPEC.variable)) AS VARIABLE,
                TRIM(SPEC.type) AS TYPE, TRIM(SPEC.format) AS FORMAT, TRIM(SPEC.label) AS LABEL, TRIM(SPEC.SDTM_DOMAIN) AS SDTM_DOMAIN, TRIM(SPEC.SDTM_VARIABLE) AS SDTM_VARIABLE,TRIM(SPEC.MAPPING_COMMENTS) AS MAPPING_COMMENTS, TRIM(SPEC.PIVOT) AS PIVOT, TRIM(SPEC.notes)
                AS NOTES, TRIM(SPEC.MAPPING_QUESTIONS) AS MAPPING_QUESTIONS FROM MAP_ADMIN.SDTM32 MODELED_ EXTENDED SDTM LEFT OUTER JOIN MAP_ADMIN2.SP_SPECS SPEC ON TRIM(SPEC.SDTM DOMAIN) = 'AE' AND SDTM.VARIABLE_NAME = UPPER(TRIM(SPEC.SDTM_VARIABLE)) AND
                (SPEC.PIVOT IS NULL OR UPPER(TRIM(REPLACE(SPEC.PIVOT,''))) = '1' OR REPLACE(SPEC.PIVOT,') LIKE '1;%' OR REPLACE(SPEC.PIVOT,'') LIKE '%; OR OR REPLACE(SPEC.PIVOT,') LIKE '%;1;%') AND LIST_ID = 1494 WHERE
                SDTM.DOMAIN_NAME = 'AE' ORDER BY SDTM. VARIABLE_POSITION");
                jelement.put("startTime", "Start time = 2016-05-12T07:29:32. Creating domain: AE");
                jelement.put("stm _domain_name","stm domain_ name: AE, domain _class:EVENTS");
                jelement.put("sourceSchema", "Source schema = TEST32_SCHEMA, Target schema =TEST32_SCHEMA, StudyID =, SDTM Version = 3.2, Domain Class = EVENTS, Table Structure =MAP_ADMIN SDTM32 _MODELED _EXTENDED.");
                jelement.put("output _code_variable", "output_code _variable = CAST('AE' AS VARCHAR2(2 BYTE)) AS DOMAIN");
                jelement.put("mesgl", "Previous tables backed up successfully."); 
                jelement.put("mesg2", "Attempting to execute INSERT statement for AE 2016-05-12T07:29:34");
                jelement.put("mesg3"," AE created -- 16 records. Begin executing post mapping processes 2016-05-12T07:29:35");
                jelement.put("mesg4", "Indexes created or verified - 2016-05-12T07:29:35");
                jelement.put("mesgS", "Executed DY_DERIVATIONS_ PROC_NEW start = 2016-05-12T07:29:35, end = 2016-05-12T07:29:36");
                jelement.put("mesg6", "IDVARVAL post mapping process will not be run");
                jelement.put("mesg7", "Executed ADD_SUBJSEQ_PROC start = 2016-05-12T07:29:36,end = 2016-05-12T07:29:36");
                jelement.put("mesg®","SDTM_TABLE_NAME is AE_ STD_FINAL and var is STUDYID");
                jelement.put("mesg", "SDTM_ TABLE NAME is AE_ STD_FINAL and var is DOMAIN");
                jelement.put("mesg10", "SDTM_ TABLE_NAME is AE_STD_FINAL and var is USUBJID");
                jelement.put("mesg I","SDTM_ TABLE_NAME is AE_ STD_FINAL and var is AESEQ");
                jelement.put("mesg12", "STM_TABLE_NAME is AE_STD_FINAL and var is AEREFID");
                jelement.put("mesg13", "SDTM_TABLE_NAME is AE_STD_FINAL and var is AETERM");
                jelement.put("mesg37" "Executed DROP_NULL_TRIM start = 2016-05-12T07:29:36,end = 2016-05-12T07:29:39");
                jelement.put("mesg38", "End time = 2016-05-12T07:29:39");
                logmesgarray.add(jelement);
                json.put("LOGS", logmesgarray);
            } else if (request.getParameter("task").equals("expand_logmesgSql")) {
                JSONArray logmesgsqlarray = new JSONArray();
                jelement.put("sqlText", "SELECT * FROM ( SELECT * FROM ( SELECT
                CAST(TRIM(SUBSTR (2014-10-Study-A', 1,200)) AS VARCHAR2(200 BYTE)) AS STUDYID, VARCHAR2(2 BYTE)) AS DOMAIN,
                CAST('AE' AS
                CAST(TRIM(SUBSTR('2014-10-Study-A-001-||substrAE_CD.subjid,5,3), 1,200))
                AS VARCHAR2(200 BYTE)) AS USUBJID, CASTNULL AS VARCHAR2(200 BYTE)) AS POOLID, CASTNULL AS VARCHAR2(200 BYTE)) AS SPDEVID, CAST(NULL AS NUMBER) AS AESEQ, CAST NULL AS
                VARCHAR2(200 BYTE)) AS AEGRPID, CAST(TRIM(SUBSTR(dense_rank) over (order by aeterm desc), 1,200)) AS VARCHAR2(200 BYTE)) AS AEREFID, CAST(NULL AS VARCHAR2(200 BYTE)) AS AESPID, CAST/NULL AS VARCHAR2(200 BYTE)) AS AELNKID, CASTNULL AS VARCHAR2(200 BYTE)) AS AELNKGRP,
                CAST(TRIM(SUBSTR(upper(trim(ae_cd.aeterm)),1,200)) AS VARCHAR2(200 BYTE)) AS AETERM, CAST(NULL AS
                VARCHAR2(200 BYTE)) AS AEMODIFY, BYTE)) AS AELLT,
                CAST(TRIM(SUBSTR(AE_CD.LLT_NM,1,200)) AS VARCHAR2(200
                CAST(AE_CD.LLT_CD AS NUMBER) AS AELLTCD,
                CAST(TRIM(SUBSTR(AE_CD.PT_NM,1,200)) AS VARCHAR2(200 BYTE)) AS AEDECOD, CAST(AE_CD.PT_CD AS
                NUMBER) AS AEPTCD, CAST(TRIM(SUBSTR(AE_CD.HLT_NM,1,200)) AS VARCHAR2(200 BYTE)) AS AEHLT,
                CAST(AE_CD.HLT_CD AS NUMBER) AS AEHLTD, CAST(TRIM(SUBSTR(AE_CD.HLGT_NM,1,200)) AS
                VARCHAR2(200 BYTE)) AS AEHLGT, CAST(AE_CD.HLGT_CD AS NUMBER) AS AEHLGTCD, CASTNULL AS
                VARCHAR2(200 BYTE)) AS AECAT, CAST(NULL AS VARCHAR2(200 BYTE)) AS AESCAT, CASTNULL AS
                VARCHAR2(2 BYTE)) AS AEPRESP, CAST(NULL AS VARCHAR2(2 BYTE)) AS AEOCCUR, CASTNULL AS
                VARCHAR2(8 BYTE)) AS AESTAT, CAST(NULL AS VARCHAR2(200 BYTE)) AS AEREASND,
                CAST(TRIM(SUBSTR(AE_CD.SOC_NM,1,200)) AS VARCHAR2(200 BYTE)) AS AEBODSYS,
                CAST(AE_CD.SOC_CD AS NUMBER) AS AEBDSYCD, CAST(TRIM(SUBSTR(AE_CD.SOC_NM,1,200)) AS
                VARCHAR2(200 BYTE)) AS AESOC, CAST(AE_CD.SOC_CD AS NUMBER) AS AESOCCD, CAST(NULL AS
                VARCHAR2(200 BYTE)) AS AELOC, CAST NULL AS VARCHAR2(200 BYTE)) AS AELAT, CAST/NULL AS
                VARCHAR2(200 BYTE)) AS AEDIR, CAST(NULL AS VARCHAR2(200 BYTE)) AS AEPORTOT, CAST(NULL AS
                VARCHAR2(200 BYTE)) AS AEPARTY, CAST(NULL AS VARCHAR2(200 BYTE)) AS AEPRTYID,
                CAST(TRIM(SUBSTR(UPPER(TRIM(AE_CD.AESEV)), 1,200)) AS VARCHAR2(200 BYTE)) AS AESEV,
                CAST(TRIM(SUBSTR(CASE WHEN AE_CD.AESER = 'No' THEN 'N' WHEN AE_CD.AESER = 'Yes' THEN 'Y' END,1,2))
                AS VARCHAR2(2 BYTE)) AS AESER, CAST(TRIM(SUBSTR(UPPER(TRIM(AE_CD.AEACN)), 1,200)) AS
                VARCHAR2(200 BYTE)) AS AEACN, CAST(TRIM(SUBSTR(CASE WHEN AE_CD.AENONE = 'checked' THEN
                'NONE' WHEN AE_CD.AEMED = 'checked' AND AE_CD.AENDT IS NULL THEN 'MEDICATION' END, 1,200)) AS
                VARCHAR2(200 BYTE)) AS AEACNOTH, CAST(NULL AS VARCHAR2(200 BYTE)) AS AEACNDEV,
                CAST(TRIM(SUBSTR(UPPER(AEREL), 1,200)) AS VARCHAR2(200 BYTE)) AS AEREL, CAST(NULL AS VARCHAR2(200 BYTE)) AS AERELNST, CAST(NULL AS VARCHAR2(200 BYTE)) AS AEPATT,
                CAST(TRIM(SUBSTR(CASE WHEN AE_CD.AEOUT = 'Resolved' THEN 'RECOVERED/RESOLVED' WHEN
                AE_CD.AEOUT = 'Not resolved' THEN 'NOT RECOVERED/NOT RESOLVED' END, 1,200)) AS VARCHAR2(200 BYTE))
                AS AEOUT, CAST(NULL AS VARCHAR2(2 BYTE)) AS AESCAN, CAST(NULL AS VARCHAR2(2 BYTE)) AS CAST(NULL AS VARCHAR2(2 BYTE)) AS AESDISAB, CAST(NULL AS VARCHAR2(2 BYTE)) AS CAST(NULL AS VARCHAR2(2 BYTE)) AS AESHOSP, CAST(NULL AS VARCHAR2(2 BYTE)) AS
                AESLIFE, CAST(NULL AS VARCHAR2(2 BYTE)) AS AESOD, CAST(NULL AS VARCHAR2(2 BYTE)) AS
                CAST(TRIM(SUBSTR(CASE WHEN AE_CD.AENONE = 'checked' THEN N' WHEN AE_ CD.AEMED =
                'checked' AND AE_CD.AENDT IS NULL THEN 'Y' END,1,2)) AS VARCHAR2(2 BYTE)) AS AECONTRT,
                CAST(NULL AS VARCHAR2(1 BYTE)) AS AETOXGR,
                CAST NULL AS
                AS VARCHAR2(200 BYTE)) AS AETOX,
                NUMBER) AS VISITNUM, CAST(NULL AS VARCHAR2(200 BYTE)) AS VISIT, CAST(NULL AS NUMBER) AS
                VISITDY, CAST(NULL AS NUMBER) AS TAETORD, CASTNULL AS VARCHAR2(200 BYTE)) AS EPOCH,
                CASTNULL AS VARCHAR2(8 BYTE)) AS ETCD, CASTNULL AS VARCHAR2(200 BYTE)) AS ELEMENT,
                CASTNULL AS VARCHAR2(200 BYTE)) AS AEDTC, CAST(TRIM(SUBSTRCASE WHEN AE_CD.AESTTM IS NOT
                NULL THEN AE_CD.AESDC||T||AE CD. AESTTM ELSE AE_CD.AESDC END,1,200)) AS V ARCHAR2(200 BYTE)) AS AESTDTC,
                CAST(TRIM(SUBSTRCASE WHEN AE_CD. AEENDTM IS NOT NULL THEN
                AE_CD.AESTD||T||AE_CD.AEENDTM ELSE AE_CD.AESTD END, 1,200)) AS VARCHAR2(200 BYTE)) AS ABENDTC,
                CAST(NULL AS NUMBER) AS AEDY, CAST(NULL AS NUMBER) AS AESTDY, CAST(NULL AS NUMBER) AS
                ABENDY,
                CAST(NULL AS VARCHAR2(200 BYTE)) AS AEDUR, CAST(NULL AS VARCHAR2(200 BYTE)) AS
                AETPT.
                CAST(NULL AS NUMBER) AS AETPTNUM, CASTNULL AS VARCHAR2(200 BYTE)) AS AEELTM,
                CAST(NULL AS VARCHAR2(200 BYTE)) AS AETPTREF, CAST(NULL AS VARCHAR2(200 BYTE)) AS AERFTDTC,
                CAST(NULL AS VARCHAR2(12 BYTE)) AS AESTRF, CAST NULL AS VARCHAR2(12 BYTE)) AS AEENRF,
                CAST(NULL AS VARCHAR2(200 BYTE)) AS AEEVLINT, CAST(NULL AS VARCHAR2(200 BYTE)) AS AEEVINTX, CASTNULL AS VARCHAR2(10 BYTE)) AS AESTRTPT, CAST(NULL AS VARCHAR2(200 BYTE)) AS AESTTPT,
                CAST(NULL AS VARCHAR2(10 BYTE)) AS AEENRTPT, CAST(NULL AS VARCHAR2(200 BYTE)) AS ABENTPT, CAST(NULL AS VARCHAR2(200 BYTE)) AS AESTINT, CAST NULL AS VARCHAR2(200 BYTE)) AS AEENINT,
                CAST(NULL AS NUMBER) AS AEDETECT, CAST(AE_CD.OCTA_SEQ || ':000001' AS VARCHAR2(200)) AS
                OCTA_SEQ from ANOTHERSCHEMA.ae_cd_source ae_cd) WHERE AETERM IS NOT NULL)");
                logmesgsqlarray.add(jelement);
                json.put("LOGS", logmesgsqlarray);
            } else if (request.getParameter("task").equals("load_a_spec")) {
                JSONArray logmesgDesarray = new JSONArray();
                jelement.put("fileText", "DCS file uploaded successfully");
                logmesgDcsarray.add(jelement);
                json.put("LOGS", logmesgDcsarray);
            ) else if (request.getParameter ("task").equals("run_Idspecs")) {
                JSONArray logldspecarray = new JSONArray();
                jelement.put("logText", "DCS file loaded into DB successfully");
                logldspecarray.add (jelement);
                json.put("LOGS", logldspecarray);
            } else if (request.getParameter("task").equals("run_Idviews")) {
                JSONArray logldspecarray = new JSONArray();
                jelement.put("logText", "DCS views loaded into DB successfully");
                logldspecarray.add(jelement);
                json.put("LOGS", logldspecarray);
            } else if (request.getParameter("task").equals("run_crtviews")) {
                JSONArray logldspecarray = new JSONArray();
                jelement.put("logText", "View created successfully");
                logldspecarray.add(jelement);
                json.put("LOGS",logldspecarray);
            } else if (request.getParameter("task").equals("select_a_view")) {
                JSONArray logldspecarray = new JSONArray();
                jelement.put("logText", "View code select success");
                logldspecarray.add(jelement);
                json.put("LOGS", logldspecarray);
            } else if (request.getParameter ("task"). equals("select_a_spec")) {
                JSONArray logldspecarray = new JSONArray();
                jelement.put("logText", "Spec select success");
                logldspecarray.add(jelement);
                json.put("LOGS", log|dspecarray);
            } else if (request.getParameter("task").equals("add_client")) {
                JSONArray logldspecarray = new JSONArray();
                jelement.put("logText", "Client Added success"); 
                logldspecarray.add(jelement);
                json.put("LOGS", logldspecarray);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8"); 
                response.getWriter().write(json.toString);
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }
}