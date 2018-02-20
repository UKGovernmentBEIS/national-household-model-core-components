package uk.org.cse.nhm.language.definition.enums;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;


@Doc("Each of these defines an end-use for energy that the energy calculator understands.")
@Category(CategoryType.CATEGORIES)
public enum XServiceType {
	Cooking(ServiceType.COOKING),
	Lighting(ServiceType.LIGHTING),
	Appliances(ServiceType.APPLIANCES),
	SpaceHeating(ServiceType.PRIMARY_SPACE_HEATING, ServiceType.SECONDARY_SPACE_HEATING),
	MainSpaceHeating(ServiceType.PRIMARY_SPACE_HEATING),
	SecondarySpaceHeating(ServiceType.SECONDARY_SPACE_HEATING),
	WaterHeating(ServiceType.WATER_HEATING),
	Generation(ServiceType.GENERATION);

	private ImmutableList<ServiceType> serviceTypes;

	private XServiceType(final ServiceType... serviceTypes) {
		this.serviceTypes = ImmutableList.copyOf(
				serviceTypes
		);
	}

	public List<ServiceType> getInternal() {
		return serviceTypes;
	}

}
