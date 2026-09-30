package ru.lakeevdan.camundaapp.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lakeevdan.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevdan.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevdan.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevdan.camundaapp.application.port.out.repository.TicketRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketUseCaseImpl implements TicketUseCase {
    private final TicketRepository repository;

    @Override
    public List<TicketParamResponse> getByUserId(Long userId) {
        return List.of();
    }

    @Override
    public void create(TicketParamRequest param) {

    }

    @Override
    public void delete(Long ticketId) {

    }
}
