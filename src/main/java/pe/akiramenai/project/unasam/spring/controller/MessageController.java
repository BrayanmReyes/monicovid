package pe.akiramenai.project.unasam.spring.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.akiramenai.project.unasam.spring.mail.HttpJavaAltiria;
import pe.akiramenai.project.unasam.spring.mail.MailSend;
import pe.akiramenai.project.unasam.spring.model.Contacto;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.service.IContactoService;
import pe.akiramenai.project.unasam.spring.service.IReporteService;
import pe.akiramenai.project.unasam.spring.service.ISesionService;
import pe.akiramenai.project.unasam.spring.service.ITemperaturaService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;

@RestController
@RequestMapping("api/sms")
public class MessageController {
	
	@Autowired
	private IContactoService aService;
		
	@Autowired
	private IUsuarioService uService;
	
	@Autowired
	private ITemperaturaService tService;
	
	@Autowired
	private ISesionService sService;
	
	@Autowired
	private IReporteService rService;
	
	private String obtenerUsuario() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
		String userName = userDetails.getUsername();
		return userName;
	}
	
	private Usuario obtenerUsuarioxUsername() {
		return uService.buscarPorUserName(obtenerUsuario());
	}
	@RequestMapping("/prueba")
	public static void SMS(String[] args) {
	  
	  
      try {
          // Construct data
          String apiKey = "apikey=" + "MzU2ZDM2NzE0Mzc0NzU2MjUwNGE2ZjQ5NmY0MjZiNGQ=";
          String message = "&message=" + "MoniCovid te informa que su amigo/familiar se encuentra inestable";
          String sender = "&sender=" + "MoniCovid";
          String numbers = "&numbers=" + "51993802367";

          // Send data
          HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.com/send/?").openConnection();
          String data = apiKey + numbers + message + sender;
          conn.setDoOutput(true);
          conn.setRequestMethod("POST");
          conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
          conn.getOutputStream().write(data.getBytes("UTF-8"));
           
          BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          StringBuffer stringBuffer = new StringBuffer();
          String line;
          while ((line = rd.readLine()) != null) {
              stringBuffer.append(line).append("\n");
          }
          
          System.out.println(stringBuffer.toString());
          rd.close();


      } catch (Exception e) {
         e.printStackTrace(); }
	         

    }
		 
	@RequestMapping("/sms")
	public void testSMS(){
		  
		  List<Contacto> listContactos = aService.listarContactosbyUsername(obtenerUsuario());
		  
	      try {
	    	  		
    	  		RequestConfig config = RequestConfig.custom()
    			  .setConnectTimeout(5000)
    			  .setSocketTimeout(60000)
    			  .build();
    			 	
    			 //Se inicia el objeto HTTP
    			 HttpClientBuilder builder = HttpClientBuilder.create();
    			 builder.setDefaultRequestConfig(config);
    			 CloseableHttpClient httpClient = builder.build();
    			 	
    			 //Se fija la URL sobre la que enviar la petici�n POST
    			 HttpPost post = new HttpPost("http://www.altiria.net/api/http");
    			 	
    			 //Se crea la lista de par�metros a enviar en la petici�n POST
    			 List<NameValuePair> parametersList = new ArrayList <NameValuePair>();
    			 //XX, YY y ZZ se corresponden con los valores de identificaci�n del
    			 //usuario en el sistema.
    			 parametersList.add(new BasicNameValuePair("cmd", "sendsms"));
    			 //domainId solo es necesario si el login no es un email
    			 //parametersList.add(new BasicNameValuePair("domainId", "XX"));
    			 parametersList.add(new BasicNameValuePair("login", "monicovid.no.reply@gmail.com"));
    			 parametersList.add(new BasicNameValuePair("passwd", "pbdncegy"));
    			 
    			 listContactos.forEach((contacto)->{
    				 parametersList.add(new BasicNameValuePair("dest", contacto.getNumero()));
        		});
    			 String mensaje = "¡EMERGENCIA! El paciente "+ obtenerUsuarioxUsername().getNombre() + " "+ obtenerUsuarioxUsername().getApellido() + " presenta complicaciones en su salud. Atte. MONICOVID";
    			 //parametersList.add(new BasicNameValuePair("dest", "346yyyyyyyy"));
    			 parametersList.add(new BasicNameValuePair("msg", mensaje));
    			 //No es posible utilizar el remitente en Am�rica pero s� en Espa�a y Europa
    			 //Descomentar la l�nea solo si se cuenta con un remitente autorizado por Altiria
    			 //parametersList.add(new BasicNameValuePair("senderId", "remitente"));
    			 	
    			 try {
    			  //Se fija la codificacion de caracteres de la peticion POST
    			  post.setEntity(new UrlEncodedFormEntity(parametersList,"UTF-8"));
    			 }
    			 catch(UnsupportedEncodingException uex) {
    			  System.out.println("ERROR: codificaci�n de caracteres no soportada");
    			 }
    			 	
    			 CloseableHttpResponse response = null;
    			 	
    			 try {
    			  System.out.println("Enviando petici�n");
    			  //Se env�a la petici�n
    			  response = httpClient.execute(post);
    			  //Se consigue la respuesta
    			  String resp = EntityUtils.toString(response.getEntity());
    			 	    
    			  //Error en la respuesta del servidor
    			  if (response.getStatusLine().getStatusCode()!=200){
    			    System.out.println("ERROR: C�digo de error HTTP:  " + response.getStatusLine().getStatusCode());
    			    System.out.println("Compruebe que ha configurado correctamente la direccion/url ");
    			    System.out.println("suministrada por Altiria");
    			    return;
    			  }else {
    			    //Se procesa la respuesta capturada en la cadena 'response'
    			    if (resp.startsWith("ERROR")){
    			     System.out.println(resp);
    			     System.out.println("C�digo de error de Altiria. Compruebe las especificaciones");
    			     	
    			    } else
    			     System.out.println(resp);
    			    	
    			    }
    			  }
    			  catch (Exception e) {
    			    System.out.println("Excepci�n");
    			    e.printStackTrace();
    			    return;
    			  }
    			  finally {
    			     //En cualquier caso se cierra la conexi�n
    			     post.releaseConnection();
    			     if(response!=null) {
    			      try {
    			        response.close();
    			      }
    			      catch(IOException ioe) {
    			        System.out.println("ERROR cerrando recursos");
    			        
    			      }
    			     }
    			     
    			     
    			  }
    			
      } catch (Exception e) {
         e.printStackTrace();
         }
    }
	
	
	public void enviarAlerta() {
		
		
		uService.listarPacientes().forEach((usuario)->{
			String mensajeString = "";
			if(tService.obtenerUltimoRegistro(usuario.getUsername())!=null) {
				//Último registro
				Date horaMonitoreo = tService.obtenerUltimoRegistro(usuario.getUsername());
				
				//Añadir 12h a la hora del ultimo registro
				Calendar horaC = Calendar.getInstance();
				horaC.setTime(horaMonitoreo);
				horaC.add(horaC.HOUR, +12);
				Date horaMonitoreoMas12 = horaC.getTime();
				
				//Hora actual de la alerta
				Date horaActual = new Date();
				
				//Hora del ultimo monitoreo + 8h
				Calendar horaM = Calendar.getInstance();
				horaM.setTime(horaMonitoreo);
				horaM.add(horaM.HOUR, +8);
				Date horaMonitoreoMas8 = horaM.getTime();
				
				//Comparar monitoreo
				//Si es positivo la hora Actual es después de la hora +12h (no se registró)
				if(horaActual.compareTo(horaMonitoreoMas12)>0)
					mensajeString = "Hola "+ usuario.getNombre() + ", tienes que ser constante en tus monitoreos si quieres cuidar tu salud. Por favor, monitoreate según las horas recomendadas";
				else{
					//Si es negativo la hora Actual es antes de la hora +12h (sí se registró)
					//formateando fecha
					SimpleDateFormat formateadorFecha = new SimpleDateFormat("hh:mm aa");
					SimpleDateFormat formateadorHora = new SimpleDateFormat("'del' dd 'de' MMMM");
					String mHora = formateadorFecha.format(horaMonitoreoMas8);
					String meridiano = "";
					if(mHora.contains("p"))
						meridiano = " PM";
					else
						meridiano = " AM";
					formateadorFecha = new SimpleDateFormat("hh:mm");
					mHora = formateadorFecha.format(horaMonitoreoMas8)+ meridiano;
					String mFecha =  formateadorHora.format(horaMonitoreoMas8);
					mensajeString = "Hola "+ usuario.getNombre() + ", no olvides realizar tu monitoreo de las " + mHora + " " + mFecha +" si usted no lo ha hecho previamente.";	
				}
				
			}
			else
				mensajeString = "Hola "+ usuario.getNombre() + ", no olvides realizar tu monitoreo";
			
			//sService.sendMail(usuario.getUsername(),"MONICOVID: Alerta de Monitoreo", mensajeString);	
			//this.enviarAlertaSMS(mensajeString, usuario);
			System.out.println(mensajeString);
			
		});
		
	}
	
	public void enviarAlertaSMS(String mensaje, Usuario usuario) {
		HttpJavaAltiria http = new HttpJavaAltiria();
		if(usuario.getNumero()!=null && usuario.getNumero()!="")
			http.sendSMSMasivo(mensaje, usuario);
	}
	
	
	@Scheduled(cron = "0 30 6 * 8 ?", zone="America/Lima")
	public void envioAlertaMañanaAgosto() {
		
		enviarAlerta();
	}
	
	@Scheduled(cron = "0 30 14 * 8 ?", zone="America/Lima")
	public void envioAlertaTardeAgosto() {
		enviarAlerta();
	}
	
	@Scheduled(cron = "0 30 22 * 8 ?", zone="America/Lima")
	public void envioAlertaNocheAgosto() {
		enviarAlerta();
	}
	
	@Scheduled(cron = "0 30 6 * 9 ?", zone="America/Lima")
	public void envioAlertaMañanaSetiembre() {
		
		enviarAlerta();
	}
	
	@Scheduled(cron = "0 30 14 * 9 ?", zone="America/Lima")
	public void envioAlertaTardeSetiembre() {
		enviarAlerta();
	}
	
	@Scheduled(cron = "0 30 22 * 9 ?", zone="America/Lima")
	public void envioAlertaNocheSetiembre() {
		enviarAlerta();
	}
  
}

