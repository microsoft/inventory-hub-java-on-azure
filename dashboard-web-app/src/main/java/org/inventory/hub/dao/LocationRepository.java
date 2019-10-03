/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package org.inventory.hub.dao;

import com.microsoft.azure.spring.data.cosmosdb.repository.ReactiveCosmosRepository;
import org.inventory.hub.model.Location;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends ReactiveCosmosRepository<Location, String> {
}
