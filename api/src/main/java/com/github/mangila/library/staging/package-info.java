/**
 * Data staging and ingestion pipeline.
 *
 * <p>Handles streaming raw OpenLibrary dump archives, bulk copying to PostgreSQL staging tables,
 * batch processing, and routing data to domain-specific services.
 */
package com.github.mangila.library.staging;
