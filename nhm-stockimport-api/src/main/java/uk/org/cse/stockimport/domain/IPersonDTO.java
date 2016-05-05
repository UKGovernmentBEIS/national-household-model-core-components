package uk.org.cse.stockimport.domain;

import uk.org.cse.nhm.hom.types.SexType;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

@DTO(value = "people", required = false, description = "A table which contains information about the people in each house.")
public interface IPersonDTO extends IBasicDTO {
    @DTOField(value = "sex", description = "The sex of the person")
    public SexType getSex();
    public void setSex(final SexType sex);

    @DTOField(value = "age", description = "The age of the person")
    public int getAge();
    public void setAge(final int age);

    @DTOField(value = "smoker", description = "Whether the person is a smoker")
    public boolean isSmoker();
    public void setSmoker(final boolean smoker);
}


