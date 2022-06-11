package com.devsuperior.primeirodesafio.service;

import com.devsuperior.primeirodesafio.dto.ClientDTO;
import com.devsuperior.primeirodesafio.entity.Client;
import com.devsuperior.primeirodesafio.repository.ClientRepository;
import com.devsuperior.primeirodesafio.service.exceptions.ControllerNotFoundException;
import com.devsuperior.primeirodesafio.service.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        try {
            Page<Client> client = clientRepository.findAll(pageRequest);
            return client.map(c -> new ClientDTO(c));
        } catch (EntityNotFoundException erro) {
            throw new ControllerNotFoundException("Not found");
        }
    }

    public ClientDTO findById(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        Client client = clientOptional.orElseThrow(() -> new ControllerNotFoundException("Client not found"));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
        clientRepository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO updateClient(ClientDTO clientDTO, Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        Client client = clientOptional.orElseThrow(() -> new ControllerNotFoundException("Client not found"));
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
        clientRepository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException erro) {
            throw new ControllerNotFoundException("ID not found " + id);
        } catch (DataIntegrityViolationException erro) {
            throw new DatabaseException("Integrity violation");
        }
    }
}