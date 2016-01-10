package com.libqa.web.view.index;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(staticName = "of")
public class IndexSpace {
    private Integer spaceId;
    private String title;
    private String description;
}
