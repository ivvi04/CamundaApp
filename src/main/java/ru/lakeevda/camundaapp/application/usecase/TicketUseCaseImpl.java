package ru.lakeevda.camundaapp.application.usecase;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lakeevda.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevda.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevda.camundaapp.application.mapper.TicketMapper;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.application.port.out.repository.TicketRepository;
import ru.lakeevda.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevda.camundaapp.domain.entity.ticket.Ticket;
import ru.lakeevda.camundaapp.domain.entity.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketUseCaseImpl implements TicketUseCase {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public List<TicketParamResponse> getByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        return ticketRepository.findByUserId(userId)
                .stream().map(TicketMapper::fromDomain)
                .toList();
    }

    @Override
    public TicketParamResponse create(TicketParamRequest param) {
        if (param == null) {
            throw new IllegalArgumentException("param is null");
        }
        User user = userRepository.findById(param.userId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("user with id %s not found", param.userId())));
        Ticket ticket = ticketRepository.save(TicketMapper.toDomain(param, user));
        return TicketMapper.fromDomain(ticket);
    }

    @Override
    public void delete(Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("ticketId is null");
        }
        ticketRepository.delete(ticketId);
    }
}
