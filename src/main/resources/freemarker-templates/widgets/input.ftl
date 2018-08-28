<#import "/spring.ftl" as spring/>
<#macro input path prompt>
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>"">
    <label for="${id}" id="${id + '_label'}" class="govuk-label--m">${prompt}</label>
    <#if hasError><span class="govuk-error-message">${spring.status.errorMessages?join(" ")}</span></#if>
    <input type="text" id="${id}" name="${spring.status.expression}" value="${spring.stringStatusValue}" class="govuk-input <#if hasError>govuk-input--error</#if>"/>
  </div>
</#macro>

<#macro radioBoolean path questionPrompt yesPrompt="Yes" noPrompt="No">
  <@spring.bind path/>

  <#local id=spring.status.expression?replace('[','')?replace(']','')>
  <#local hasError=(spring.status.errorMessages?size > 0)>

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
    <fieldset class="govuk-fieldset">
      <legend class="govuk-fieldset__legend govuk-fieldset__legend--m">${questionPrompt}</legend>
      <#if hasError><span class="govuk-error-message">${spring.status.errorMessages?join(" ")}</span></#if>
      <div class="govuk-radios govuk-radios--inline">
        <div class="govuk-radios__item">
          <input name="${spring.status.expression}" id="${id + '_true'}" class="govuk-radios__input" type="radio" value="true" <#if spring.stringStatusValue == 'true'> checked="checked"</#if>>
          <label class="govuk-label govuk-radios__label"  for="${id + '_true'}" id="${id + '_true_label'}">
            ${yesPrompt}
          </label>
        </div>
        <div class="govuk-radios__item">
          <input name="${spring.status.expression}" id="${id + '_false'}" class="govuk-radios__input" type="radio" value="false" <#if spring.stringStatusValue == 'false'> checked="checked"</#if>>
          <label class="govuk-label govuk-radios__label" for="${id + '_false'}" id="${id + '_false_label'}">
            ${noPrompt}
          </label>
        </div>
      </div>
    </fieldset>
  </div>

</#macro>

<#macro checkboxes path prompt options>
  <@spring.bind path/>

  <#local hasError=(spring.status.errorMessages?size > 0)>

  <div class="govuk-form-group <#if hasError>govuk-form-group--error</#if>">
    <fieldset class="govuk-fieldset">
      <legend class="govuk-fieldset__legend govuk-fieldset__legend--m">${prompt}</legend>
      <#if hasError><span class="govuk-error-message">${spring.status.errorMessages?join(" ")}</span></#if>
      <span id="${id}-hint" class="govuk-hint">Select all that apply.</span>
      <div class="govuk-checkboxes">
        <#list options?keys as option>
          <div class="govuk-checkboxes__item">

            <#local id="${spring.status.expression?replace('[','')?replace(']','')}${option_index}">
            <#assign isSelected = spring.contains(spring.status.actualValue?default([""]), option)>

            <input type="checkbox" id="${id}" name="${spring.status.expression}" value="${option}" class="govuk-checkboxes__input"<#if isSelected> checked="checked"</#if>/>
            <label for="${id}" class="govuk-label govuk-checkboxes__label">
              ${options[option]}
            </label>
          </div>
        </#list>
        <input type="hidden" name="_${spring.status.expression}" value="on"/>
      </div>
    </fieldset>
  </div>
</#macro>