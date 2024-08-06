package mr.buddies.projects.ScrutinyGlobal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mr.buddies.projects.ScrutinyGlobal.dto.ProjectRequest;
import mr.buddies.projects.ScrutinyGlobal.dto.ProjectResponse;
import mr.buddies.projects.ScrutinyGlobal.dto.RegisterRequest;
import mr.buddies.projects.ScrutinyGlobal.dto.VenderAddToProject;
import mr.buddies.projects.ScrutinyGlobal.exception.ErrorMsgException;
import mr.buddies.projects.ScrutinyGlobal.exception.UserAlreadyExist;
import mr.buddies.projects.ScrutinyGlobal.model.DocumentDetails;
import mr.buddies.projects.ScrutinyGlobal.model.ProjectDetails;
import mr.buddies.projects.ScrutinyGlobal.model.SurveyListDetails;
import mr.buddies.projects.ScrutinyGlobal.model.VenderMappingDetails;
import mr.buddies.projects.ScrutinyGlobal.repo.ProjectRepository;
import mr.buddies.projects.ScrutinyGlobal.services.ProjectService;
import mr.buddies.projects.ScrutinyGlobal.services.SurveyService;
import mr.buddies.projects.ScrutinyGlobal.services.VenderMappingService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ScrutinyGlobal")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private SurveyService surveyService;
	
	
	
	
	@PostMapping("/saveProjectDetails")
	public ResponseEntity<?> saveProjectDetails(@RequestBody ProjectRequest projectRequest) throws ErrorMsgException, UserAlreadyExist {
	
		ProjectResponse ProjectResponse=new ProjectResponse();
		String SurveyMappingId=""; 
		if(!projectRequest.getSurveyList().isEmpty()) {
			Random random = new Random();
			int number=1000 + random.nextInt(9000);	
			SurveyMappingId="S"+number;
		}
		
		ProjectDetails projectDetails=new ProjectDetails();
		projectDetails.setProjectName(projectRequest.getProjectName());
		projectDetails.setDescription(projectRequest.getDescription());
		projectDetails.setProjectHeadId(projectRequest.getProjectHeadId());
		projectDetails.setPreSaleSPOC(projectRequest.getPreSaleSPOC());
		projectDetails.setClientMappingId(projectRequest.getClientMappingId());
		projectDetails.setSurveyMappingId(SurveyMappingId);
		projectDetails.setProjectMinimumFee(projectRequest.getProjectMinimumFee());
		projectDetails.setProjectStartTime(projectRequest.getProjectStartTime());
		projectDetails.setProjectEndTime(projectRequest.getProjectEndTime());
		projectDetails.setProjectCreateTime(new Date());
		projectDetails.setProjectUpdateTime(new Date());
		
		Integer result= projectService.saveProject(projectDetails);
		if(result !=0 && !projectRequest.getSurveyList().isEmpty()) {
			List<SurveyListDetails> surveyList=new ArrayList<>();
			 for (SurveyListDetails surveyItems : projectRequest.getSurveyList()) {
					 SurveyListDetails surveyDetail=new SurveyListDetails();
					 surveyDetail.setProjectMappingId(SurveyMappingId);
					 surveyDetail.setCountry(surveyItems.getCountry());
					 surveyDetail.setFr(surveyItems.getFr());
					 surveyDetail.setLoi(surveyItems.getLoi());
					 surveyDetail.setSurveyURL(surveyItems.getSurveyURL());
					 surveyDetail.setCpi(surveyItems.getCpi());
					 surveyDetail.setTargetAudience(surveyItems.getTargetAudience());
					 surveyDetail.setCompleteNeeds(surveyItems.getCompleteNeeds());
					 surveyDetail.setCompletePhysical(surveyItems.getCompletePhysical());
					 surveyDetail.setRate(surveyItems.getRate());
					 surveyList.add(surveyDetail);					 
				 
		        }
			 surveyService.saveSurveyLis(surveyList);			
			
		}	
		
	
		 return ResponseEntity.ok(result);
	}
	
	@PostMapping("/saveVenderDetails")
	public ResponseEntity<?> saveVenderDetails(@RequestBody VenderAddToProject venderAddToProject) throws ErrorMsgException, UserAlreadyExist {
		
		 
		 
		 boolean result=projectService.setVenderMappingDetails(venderAddToProject);
		
		 return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getProjectList")
	public ResponseEntity<?> getProjectList() throws Exception {
		
		 return ResponseEntity.ok(projectService.getProjectList());
		
	}
	
	@GetMapping("/getProjectVenderList")
	public ResponseEntity<?> getProjectVenderList(@RequestParam(name = "venderMappingId") String venderMappingId) throws Exception {
		
		 return ResponseEntity.ok(projectService.getProjectVenderList(venderMappingId));
		
	}
	
	
	@PostMapping("/uploadDoc")
    public String uploadFile(@RequestParam("type") String type,@RequestParam("mappingId") Integer mappingId,@RequestParam("file") MultipartFile file) {
        try {
        	
        	DocumentDetails documentDetails=new DocumentDetails();
        	documentDetails.setFileName(file.getName());
        	documentDetails.setFileType(file.getContentType());
        	documentDetails.setSize(file.getSize());
        	documentDetails.setData(file.getBytes());
        	if(type.equals("project")) {
        		projectService.setDocumentDetailsForProject(mappingId,documentDetails);
        	}
        	if(type.equals("vender")) {
        		projectService.setDocumentDetailsForVender(mappingId, documentDetails);
        	}
        	
        	
//         registerUserService
        	
            return "File uploaded successfully: " + file.getOriginalFilename();
        } catch (Exception e) {
            return "Failed to upload file: " + file.getOriginalFilename();
        }
    }

}
