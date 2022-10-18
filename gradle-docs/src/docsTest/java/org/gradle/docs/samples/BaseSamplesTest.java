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

import org.gradle.integtests.fixtures.executer.MoreMemorySampleModifier;
import org.gradle.integtests.fixtures.logging.ArtifactResolutionOmittingOutputNormalizer;
import org.gradle.integtests.fixtures.logging.ConfigurationCacheOutputNormalizer;
import org.gradle.integtests.fixtures.logging.DependencyInsightOutputNormalizer;
import org.gradle.integtests.fixtures.logging.GradleWelcomeOutputNormalizer;
import org.gradle.integtests.fixtures.logging.NativeComponentReportOutputNormalizer;
import org.gradle.integtests.fixtures.logging.SampleOutputNormalizer;
import org.gradle.integtests.fixtures.mirror.SetMirrorsSampleModifier;
import org.gradle.samples.test.normalizer.FileSeparatorOutputNormalizer;
import org.gradle.samples.test.normalizer.GradleOutputNormalizer;
import org.gradle.samples.test.normalizer.JavaObjectSerializationOutputNormalizer;
import org.gradle.samples.test.runner.SampleModifiers;
import org.gradle.samples.test.runner.SamplesOutputNormalizers;

@SamplesOutputNormalizers({
    SampleOutputNormalizer.class,
    JavaObjectSerializationOutputNormalizer.class,
    FileSeparatorOutputNormalizer.class,
    GradleWelcomeOutputNormalizer.class,
    GradleOutputNormalizer.class,
    ArtifactResolutionOmittingOutputNormalizer.class,
    NativeComponentReportOutputNormalizer.class,
    DependencyInsightOutputNormalizer.class,
    ConfigurationCacheOutputNormalizer.class
})
@SampleModifiers({
    SetMirrorsSampleModifier.class,
    MoreMemorySampleModifier.class
})
/*
 * To run the samples tests:
 *
 * Say you want to run the snippet found at:
 *    src/snippets/dependencyManagement/customizingResolution-consistentResolution
 *
 * then you can run the followng command line:
 *
 * ./gradlew :docs:docsTest --tests "*.snippet-dependency-management-customizing-resolution-consistent-resolution*"
 *
 * which would run both Groovy and Kotlin tests.
 */
abstract class BaseSamplesTest {
}

