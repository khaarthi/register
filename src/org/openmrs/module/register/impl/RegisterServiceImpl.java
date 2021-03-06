/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.register.impl;

import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.register.RegisterService;
import org.openmrs.module.register.db.RegisterDAO;
import org.openmrs.module.register.db.hibernate.Register;
import org.openmrs.module.register.db.hibernate.RegisterType;

public class RegisterServiceImpl extends BaseOpenmrsService implements RegisterService{
    private RegisterDAO dao;

    public void setDao(RegisterDAO dao) {
        this.dao = dao;
    }

    public List<Register> getRegisters() {
        return getRegisters(false);
    }
    
    public List<Register> getRegisters(boolean includeRetired) {
        return dao.getRegisters(includeRetired);
    }

	public Register getRegister(Integer registerId) {
		return dao.getRegister(registerId);
	}
 
    public Register saveRegister(Register register) {
        return dao.saveRegister(register);
    }

    public void deleteRegister(Integer registerId) {
    	deleteRegister(getRegister(registerId));
	}
    public void deleteRegister(Register register) {
		dao.deleteRegister(register);		
	}
    
	public List<RegisterType> getRegisterTypes() {
		return dao.getRegisterTypes();
	}

	public RegisterType getRegisterType(Integer id) {
		return dao.getRegisterType(id);
	}

	@Override
	public List<Encounter> getEncountersForRegisterByLocation(Integer registerId, Integer locationId, Integer pageSize, Integer page) {
		Location location=null;
		
		Register register = getRegister(registerId);
		Form form = register.getHtmlForm().getForm();
		LocationService locationService=Context.getLocationService();
		location=locationService.getLocation(locationId);
		return dao.getEncounters(form, location, pageSize, page);
	}
	
	@Override
	public Integer getEncounterCountForRegisterByLocation(Integer registerId, Integer locationId) {
		Location location=null;
		
		Register register = getRegister(registerId);
		Form form = register.getHtmlForm().getForm();
		LocationService locationService=Context.getLocationService();
		location=locationService.getLocation(locationId);
		return dao.getEncounterCount(form, location);
	}
	
	/**
	 * @see org.openmrs.module.register.RegisterService#getEncounters(org.openmrs.Form, org.openmrs.Location, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Encounter> getEncounters(Form form, Location location, Integer pageSize, Integer page) {
		return dao.getEncounters(form, location, pageSize, page);
	}
	
	/**
	 * @see org.openmrs.module.register.RegisterService#getEncounterCount(org.openmrs.Form, org.openmrs.Location)
	 */
	@Override
	public Integer getEncounterCount(Form form, Location location){
		return dao.getEncounterCount(form, location);
	}

	@Override
	public void deleteEncounter(Integer encounterId) {
		dao.deleteEncounter(encounterId);
	}

}
