package ru.lakeevda.camundaapp.application.usecase;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lakeevda.camundaapp.application.dto.TicketCreateUseCaseRequest;
import ru.lakeevda.camundaapp.application.dto.TicketCreateUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.TicketGetUseCaseResponse;
import ru.lakeevda.camundaapp.application.dto.TicketStartProcessUseCaseResponse;
import ru.lakeevda.camundaapp.application.mapper.TicketMapper;
import ru.lakeevda.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevda.camundaapp.application.port.out.process.CamundaProcess;
import ru.lakeevda.camundaapp.application.port.out.repository.TicketRepository;
import ru.lakeevda.camundaapp.application.port.out.repository.UserRepository;
import ru.lakeevda.camundaapp.domain.entity.ticket.Ticket;
import ru.lakeevda.camundaapp.domain.entity.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketUseCaseImpl implements TicketUseCase {
    private static final String CREATE_TICKET_PROCESS = "createTicketProcess";

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CamundaProcess camundaProcess;

    @Override
    public List<TicketGetUseCaseResponse> getByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        return ticketRepository.findByUserId(userId)
                .stream().map(TicketMapper::toGetResponse)
                .toList();
    }

    @Override
    public TicketCreateUseCaseResponse create(TicketCreateUseCaseRequest param) {
        if (param == null) {
            throw new IllegalArgumentException("param is null");
        }
        User user = userRepository.findById(param.userId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("user with id %s not found", param.userId())));
        Ticket ticket = ticketRepository.save(TicketMapper.toDomain(param, user));

        return TicketMapper.toCreateResponse(ticket);
    }

    @Override
    public void delete(Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("ticketId is null");
        }
        ticketRepository.delete(ticketId);
    }

    @Override
    public TicketStartProcessUseCaseResponse startProcess(Long userId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", userId);

        variables = camundaProcess.startProcess(CREATE_TICKET_PROCESS, variables);
        return new TicketStartProcessUseCaseResponse(
                (String) variables.get("processInstanceKey"),
                (String) variables.get("bpmnProcessId"),
                (String) variables.get("version"));
    }
}
