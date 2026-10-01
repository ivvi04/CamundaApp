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
        return ticketRepository.findByUserId(userId)
                .stream().map(TicketMapper::fromDomain)
                .toList();
    }

    @Override
    public TicketParamResponse create(TicketParamRequest param) {
        User user = userRepository.findById(param.userId())
                .orElseThrow(EntityNotFoundException::new);
        Ticket ticket = ticketRepository.save(TicketMapper.toDomain(param, user));
        return TicketMapper.fromDomain(ticket);
    }

    @Override
    public void delete(Long ticketId) {
        ticketRepository.delete(ticketId);
    }
}
