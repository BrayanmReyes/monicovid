package pe.akiramenai.project.unasam.spring.service;

import java.io.File;


public interface IUploadPathService {
	
	public File getFilePath(String modifiedFileName, String path);

}
