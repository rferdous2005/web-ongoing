package net.Askbd.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    private String firstName, lastName, email, mobile;
}
