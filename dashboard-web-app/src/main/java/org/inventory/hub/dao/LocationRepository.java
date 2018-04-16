/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package org.inventory.hub.dao;

import org.inventory.hub.model.Location;
import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends DocumentDbRepository<Location, String> {
}
