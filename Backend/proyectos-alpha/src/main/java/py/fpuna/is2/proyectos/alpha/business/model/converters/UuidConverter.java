/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.business.model.converters;

import java.util.UUID;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rafae
 */
@Converter(autoApply = true)
public class UuidConverter implements AttributeConverter<UUID, UUID> {
    @Override
    public UUID convertToDatabaseColumn(UUID attribute) {
        return attribute;
    }
    @Override
    public UUID convertToEntityAttribute(UUID dbData) {
        return dbData;
    }
}