/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.docs.samples;

import org.gradle.samples.executor.CommandExecutor;
import org.gradle.samples.executor.ExecutionMetadata;
import org.gradle.samples.model.Command;
import org.gradle.samples.test.runner.SamplesRunner;
import org.junit.runners.model.InitializationError;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Paths;

class IntegrationTestSamplesRunner extends SamplesRunner {
    private static final String SAMPLES_DIR_PROPERTY = "integTest.samplesdir";

    public IntegrationTestSamplesRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected CommandExecutor selectExecutor(ExecutionMetadata executionMetadata, File workingDir, Command command) {
        return new IntegrationTestSamplesExecutor(workingDir, command.isExpectFailure());
    }

    @Nullable
    @Override
    protected File getImplicitSamplesRootDir() {
        String samplesDir = System.getProperty(SAMPLES_DIR_PROPERTY);
        if (samplesDir == null) {
            throw new IllegalStateException(String.format("'%s' property is required", SAMPLES_DIR_PROPERTY));
        }
        return Paths.get(samplesDir).toFile();
    }
}
