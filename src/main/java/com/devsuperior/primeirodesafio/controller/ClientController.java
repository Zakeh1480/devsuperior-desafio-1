package com.devsuperior.primeirodesafio.controller;

import com.devsuperior.primeirodesafio.dto.ClientDTO;
import com.devsuperior.primeirodesafio.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAllPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "5") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<ClientDTO> clientDTOPage = clientService.findAllPaged(pageRequest);

        return ResponseEntity.status(200).body(clientDTOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> FindById(@PathVariable Long id) {
        ClientDTO clientDTO = clientService.findById(id);
        return ResponseEntity.status(200).body(clientDTO);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO newClientDTO = clientService.createClient(clientDTO);
        return ResponseEntity.status(201).body(newClientDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@RequestBody ClientDTO clientDTO, @PathVariable Long id) {
        ClientDTO client = clientService.updateClient(clientDTO, id);
        return ResponseEntity.status(200).body(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(200).build();
    }
}
