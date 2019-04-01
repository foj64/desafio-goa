package com.foj.goa.resource;


import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.foj.goa.domain.Pdv;
import com.foj.goa.services.PdvService;

@RestController
@RequestMapping(value="/pdv")
public class PdvResource {
	
	@Autowired
	private PdvService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Optional<Pdv>> find(@PathVariable Integer id) {
		Optional<Pdv> obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pdv obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/processa", method=RequestMethod.POST)
	public void processa(@RequestParam(name="data") MultipartFile data) {
		try {
			service.processa(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	@RequestMapping(path = "/tenant", method= RequestMethod.POST)
    public void createSampleOrder(@RequestHeader("tenant") String tenantName) {
        TenantContext.setCurrentTenant(tenantName);
    }
	*/

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ResponseEntity<Page<Pdv>> findPage(
			@RequestParam(value="tenant", defaultValue="") String tenant, 
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="cidade", defaultValue="") String cidade, 
			@RequestParam(value="cep", defaultValue="") String cep, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
//		String nomeDecoded = URL.decodeParam(nome);
		Page<Pdv> list = service.search(nome, cidade, cep, page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}
