package com.app.hospital.controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handlers.AppResponse;
import com.app.hospital.entity.DoctorExpEntity;
import com.app.hospital.service.DoctorExpService;
import com.app.utils.AppMessage;

@RestController
@RequestMapping("/hospital/doctorExperience")
public class DoctorExpController {
	private static final Logger logger = Logger.getLogger(DoctorExpController.class);
	@Autowired
	private DoctorExpService doctorExpService;
	@RequestMapping( value ="/getDoctorExperience",method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse getDoctorExp(@RequestParam(value = "pki_doctor_id") long pki_doctor_id,
			@RequestParam(value = "uvc_qualif_name", required = false)  String uvc_qualif_name) throws Exception {
		AppResponse response = new AppResponse();
		try {
			response.put(AppResponse.DATA_FIELD, doctorExpService.getDoctorExp(pki_doctor_id,uvc_qualif_name));
		} catch (Exception e) {
			logger.error("error in getDoctorExp :"+e.getMessage());
		}
		return response;
	}
	@RequestMapping(value = "/saveDoctorQualifications", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse saveDoctorExp(
			@RequestBody DoctorExpEntity objDoctorExp)
			throws Exception {
		AppResponse response = new AppResponse();
		try {

			DoctorExpEntity objDoctorExp1 = doctorExpService
					.getDoctorExpByIds(objDoctorExp
							.getObjQualificationsEntity()
							.getPki_doctor_qualif_master_id(),
							objDoctorExp.getObjUserEntity().getId());

			if (objDoctorExp1 == null) {
				response.put(AppResponse.DATA_FIELD, doctorExpService
						.saveDoctorExp(objDoctorExp).getPki_doctor_qualif_link_id());
			} else if (objDoctorExp.getPki_doctor_qualif_link_id() == null) {
				response.put(AppResponse.MESSAGE_FIELD,
						AppMessage.NAME_ALREADY_EXISTS);
				response.put(AppResponse.STATUS, false);
			} else if (objDoctorExp.getPki_doctor_qualif_link_id() != null
					&& objDoctorExp1.getPki_doctor_qualif_link_id() == objDoctorExp
							.getPki_doctor_qualif_link_id()) {
				response.put(AppResponse.DATA_FIELD, doctorExpService
						.saveDoctorExp(objDoctorExp).getPki_doctor_qualif_link_id());
			} else {
				response.put(AppResponse.MESSAGE_FIELD,
						AppMessage.NAME_ALREADY_EXISTS);
				response.put(AppResponse.STATUS, false);
			}
		} catch (Exception e) {
			logger.error("error in saveexperience :" + e.getMessage());
		}
		return response;
	}

	@RequestMapping(value ="/deleteDoctorQualifications/{pki_doctor_qualif_master_id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse deleteDoctorQualifications(@PathVariable("pki_doctor_qualif_master_id") long pki_doctor_qualif_link_id) throws Exception {
		AppResponse response = new AppResponse();
		try {
		int res = doctorExpService.deleteDoctorExp(pki_doctor_qualif_link_id);
		response.put(AppResponse.MESSAGE_FIELD,res==0?AppResponse.FAILURE_MESSAGE:AppResponse.SUCCESS_MESSAGE);
		response.put(AppResponse.STATUS,res==0?false:true);
		} catch (Exception e) {
			logger.error("error in deleteDoctorQualifications :"+e.getMessage());
		}
		return response;
	}
}
