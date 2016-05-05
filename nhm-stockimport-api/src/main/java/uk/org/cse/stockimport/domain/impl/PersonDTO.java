package uk.org.cse.stockimport.domain.impl;

import uk.org.cse.nhm.hom.types.SexType;
import uk.org.cse.stockimport.domain.IPersonDTO;
import uk.org.cse.stockimport.domain.geometry.impl.AbsDTO;

public class PersonDTO extends AbsDTO implements IPersonDTO {
    private SexType sex;
    
    @Override
    public SexType getSex() {
        return sex;
    }
    
    @Override
    public void setSex(SexType sex) {
        this.sex = sex;
    }

    private int age;

    
    @Override
    public int getAge() {
        return age;
    }
    
    @Override
    public void setAge(int age) {
        this.age = age;
    }

    private boolean smoker;

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

}
