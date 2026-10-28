package ru.lakeevda.camundaapp.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateRequest;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseCreateResponse;
import ru.lakeevda.camundaapp.application.dto.TicketUseCaseGetResponse;
import ru.lakeevda.camundaapp.application.exception.NotFoundException;
import ru.lakeevda.camundaapp.application.mapper.TicketMapper;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.application.port.out.repository.TicketRepository;
import ru.lakeevda.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevda.camundaapp.domain.model.ticket.Ticket;
import ru.lakeevda.camundaapp.domain.model.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketUseCaseImpl implements TicketUseCase {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public List<TicketUseCaseGetResponse> getByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        return ticketRepository.findByUserId(userId)
                .stream().map(TicketMapper::toGetResponse)
                .toList();
    }

    @Override
    public TicketUseCaseCreateResponse create(TicketUseCaseCreateRequest param) {
        if (param == null) {
            throw new IllegalArgumentException("param is null");
        }
        if (param.userId() == null) {
            throw new IllegalArgumentException("userId is null");
        }
        User user = userRepository.findById(param.userId()).orElseThrow(() ->
                new NotFoundException(String.format("user with id %s not found", param.userId())));
        Ticket ticket = ticketRepository.save(TicketMapper.toDomain(param, user));

        return TicketMapper.toCreateResponse(ticket);
    }

    @Override
    public void delete(Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("ticketId is null");
        }
        if (!ticketRepository.existsById(ticketId)) {
            throw new NotFoundException(String.format("ticket with id %s not found", ticketId));
        }
        ticketRepository.delete(ticketId);
    }
}
