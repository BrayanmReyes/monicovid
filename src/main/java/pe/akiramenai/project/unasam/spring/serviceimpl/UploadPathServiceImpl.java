package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.akiramenai.project.unasam.spring.service.IUploadPathService;

@Service
@Transactional
public class UploadPathServiceImpl implements IUploadPathService{

	@Autowired
	ServletContext context;
	

	@Override
	public File getFilePath(String modifiedFileName, String path) {
		boolean exists =new File(context.getRealPath("/"+path+"/")).exists();
		if(!exists) {
			new File(context.getRealPath("/"+path+"/")).mkdir();
		}
		
		String modifiedFilePath=context.getRealPath("/"+path+"/"+File.separator+modifiedFileName);
		File file=new File(modifiedFilePath);
		return file;
	}
	
}
