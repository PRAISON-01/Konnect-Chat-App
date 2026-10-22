package dev.praise.konnect.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AliasGeneratorServiceImpl implements AliasGeneratorService{
    private final java.lang.String[] adjectives = {
            "Fantastic", "Stunning", "Fiery", "Brilliant", "Dynamic",
            "Epic", "Fearless", "Vibrant", "Radiant", "Electric",
            "Cosmic", "Mystic", "Stellar", "Lunar", "Solar",
            "Astral", "Alpine", "Oceanic", "Gale", "Aurora",
            "Swift", "Wild", "Nimble", "Fleet", "Vortex",
            "Blaze", "Sonic", "Rapid", "Agile", "Zenith",
            "Clever", "Bold", "Chill", "Sharp", "Creative",
            "Slick", "Witty", "Astute", "Shrewd", "Canny",
            "Legendary", "Glorious", "Ancient", "Primal", "Noble",
            "Grand", "Zen", "Ethereal", "Alpha", "Apex"
    };


    private final java.lang.String[] nouns = {
            "Panda", "Falcon", "Badger", "Cheetah", "Otter",
            "Raven", "Jaguar", "Phoenix", "Leopard", "Grizzly",
            "Dolphin", "Hawk", "Cobra", "Fox", "Lynx",
            "Shadow", "Comet", "Nebula", "Vortex", "Glacier",
            "Canyon", "Summit", "Meteor", "Galaxy", "Eclipse",
            "Horizon", "Cyclone", "Tundra", "Ranger", "Avalanche",
            "Matrix", "Vector", "Pixel", "Nexus", "Quantum",
            "Cipher", "Echo", "Beacon", "Pulse", "Vertex",
            "Spark", "Blaze", "Titan", "Zenith", "Apex",
            "Rogue", "Knight", "Scout", "Saber", "Legend"
    };

    private final SecureRandom random = new SecureRandom();

    int number = 10 + random.nextInt(99);


    @Override
    public String generateAlias() {
        java.lang.String adjective = adjectives[random.nextInt(adjectives.length)];
        java.lang.String noun = nouns[random.nextInt(nouns.length)];

        int number = 10 + random.nextInt(90) + 1;

        String response = new String();
        response += adjective + noun + number;
        return response ;
    }
}
