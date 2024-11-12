package io.dating.dating_ai_backend;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.dating.dating_ai_backend.conversations.ChatMessage;
import io.dating.dating_ai_backend.conversations.Conversation;
import io.dating.dating_ai_backend.conversations.ConversationRepository;
import io.dating.dating_ai_backend.profiles.Gender;
import io.dating.dating_ai_backend.profiles.Profile;
import io.dating.dating_ai_backend.profiles.ProfileRepository;

@SpringBootApplication
public class DatingAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(DatingAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Profile profile = new Profile(
				"1",
				"John",
				"Doe",
				29,
				"White",
				Gender.MALE,
				"Hello I'm John Doe",
				"https://randomuser.me/api/portraits/men/1.jpg",
				"ISTJ");
		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);
		Conversation conversation = new Conversation(
				"1",
				profile.id(),
				List.of(
						new ChatMessage("Hello", profile.id(), LocalDateTime.now())));
		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);
	}

}
