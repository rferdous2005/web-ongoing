package net.Askbd.documents;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import net.Askbd.dtos.CategoryMenu;

import java.util.List;

@Document(collection = "misc")
@Getter
@Setter
public class Misc {
    String itemName;
    List<CategoryMenu> categories;
}
