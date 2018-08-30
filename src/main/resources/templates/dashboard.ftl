<#include "page/layout.ftl">
<@standardPage "Operator Dashboard">
  <#list dashboardEntries>
    <ul class="govuk-list--bullet">
      <#items as dashboardEntry>
        <li>ID: ${dashboardEntry.versionId} </li>
      </#items>
    </ul>
  <#else>
  <p>No dashboard entries</p>
  </#list>
  <@form formAction>
    <@button.submit "Create new application"/>
  </@form>
</@standardPage>