package com.example.enotes.config;

import java.util.Optional;

import com.example.enotes.entity.User;
import com.example.enotes.util.CommonUtil;
import org.springframework.data.domain.AuditorAware;

public class AuditAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() {
        User loggedInUser = CommonUtil.getLoggedInUser();

		return Optional.of(loggedInUser.getId());
	}

}
