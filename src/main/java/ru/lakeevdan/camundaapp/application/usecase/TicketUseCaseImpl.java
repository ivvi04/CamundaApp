package ru.lakeevdan.camundaapp.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lakeevdan.camundaapp.application.dto.TicketParamRequest;
import ru.lakeevdan.camundaapp.application.dto.TicketParamResponse;
import ru.lakeevdan.camundaapp.application.mapper.TicketMapper;
import ru.lakeevdan.camundaapp.application.port.in.usecase.TicketUseCase;
import ru.lakeevdan.camundaapp.application.port.out.repository.TicketRepository;
import ru.lakeevdan.camundaapp.application.port.out.repository.UserRepository;

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
    public void create(TicketParamRequest param) {
        userRepository.findById(param.userId())
                .ifPresent(user ->
                        ticketRepository.save(TicketMapper.toDomain(param, user)));
    }

    @Override
    public void delete(Long ticketId) {

    }
}
