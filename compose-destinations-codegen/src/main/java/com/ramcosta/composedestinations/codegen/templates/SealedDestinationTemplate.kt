package com.ramcosta.composedestinations.codegen.templates

import com.ramcosta.composedestinations.codegen.*
import com.ramcosta.composedestinations.codegen.codeGenBasePackageName
import com.ramcosta.composedestinations.codegen.codeGenDestination
import com.ramcosta.composedestinations.codegen.codeGenNoArgsDestination
import com.ramcosta.composedestinations.codegen.commons.*
import com.ramcosta.composedestinations.codegen.moduleName
import com.ramcosta.composedestinations.codegen.templates.core.FileTemplate
import com.ramcosta.composedestinations.codegen.templates.core.setOfImportable

val typeAliasDestination = "${moduleName}Destination"

const val REGION_ACTIVITY_DESTINATION_START = "[REGION_ACTIVITY_DESTINATION_START]"
const val REGION_ACTIVITY_DESTINATION_END = "[REGION_ACTIVITY_DESTINATION_END]"

val sealedDestinationTemplate = FileTemplate(
    packageStatement = "package $codeGenBasePackageName.destinations",
    imports = setOfImportable(
        "androidx.lifecycle.SavedStateHandle",
        "androidx.navigation.NavBackStackEntry",
        "$CORE_PACKAGE_NAME.spec.*",
    ),
    sourceCode = """
/**
 * Handy typealias of [$codeGenDestination] when you don't
 * care about the generic type (probably most cases for app's use)
 */
public typealias $typeAliasDestination = $codeGenDestination<*>

/**
 * $codeGenDestination is a sealed version of [$CORE_DESTINATION_SPEC]
 */
public sealed interface $codeGenDestination<T>: $CORE_DESTINATION_SPEC<T>

/**
 * $codeGenNoArgsDestination is a sealed version of [$CORE_DIRECTION_DESTINATION_SPEC]
 */
public sealed interface $codeGenNoArgsDestination: $codeGenDestination<Unit>, $CORE_DIRECTION_DESTINATION_SPEC

${REGION_ACTIVITY_DESTINATION_START}/**
 * $codeGenActivityDestination is a sealed version of [${CORE_ACTIVITY_DESTINATION_SPEC.simpleName}]
 */
public sealed interface $codeGenActivityDestination<T> : $codeGenDestination<T>, ${CORE_ACTIVITY_DESTINATION_SPEC.simpleName}<T>

/**
 * $codeGenNoArgsActivityDestination is a sealed version of [${CORE_DIRECTION_ACTIVITY_DESTINATION_SPEC.simpleName}]
 */
public sealed interface $codeGenNoArgsActivityDestination : $codeGenDestination<Unit>, $codeGenNoArgsDestination, ${CORE_DIRECTION_ACTIVITY_DESTINATION_SPEC.simpleName}${REGION_ACTIVITY_DESTINATION_END}
""".trimIndent()
)