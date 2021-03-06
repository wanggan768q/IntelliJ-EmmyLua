/*
 * Copyright (c) 2017. tangzx(love.tangzx@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tang.intellij.lua.editor.surroundWith

import com.intellij.lang.surroundWith.Surrounder
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * region surrounder
 * Created by tangzx on 2017/2/25.
 */
class RegionSurrounder : Surrounder {
    override fun getTemplateDescription(): String {
        return "Lua Region"
    }

    override fun isApplicable(psiElements: Array<PsiElement>): Boolean {
        return true
    }

    override fun surroundElements(project: Project, editor: Editor, psiElements: Array<PsiElement>): TextRange? {

        val last = psiElements[psiElements.size - 1]
        val lastTextRange = last.textRange

        val first = psiElements[0]
        val firstTextRange = first.textRange

        val document = editor.document
        val startLineNumber = document.getLineNumber(firstTextRange.startOffset)
        val startIndent = document.getText(TextRange(document.getLineStartOffset(startLineNumber), firstTextRange.startOffset))

        val endString = "\n" + startIndent + "--endregion"
        val startString = "--region description\n" + startIndent
        document.insertString(lastTextRange.endOffset, endString)
        document.insertString(firstTextRange.startOffset, startString)

        val begin = firstTextRange.startOffset + 9
        return TextRange(begin, begin + 11)
    }
}
