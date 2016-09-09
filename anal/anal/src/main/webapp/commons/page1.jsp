<table width="775" cellpadding="2" cellspacing="1" border="0">
	<tr width="775">
		<td nowrap width="35%" valign="middle">
			<ol class="tg_pages">
			<li>&nbsp;&nbsp;&nbsp;&nbsp;
			<fmt:message key="efetionmanage.common.total" />
			
			${pageInfo.resultCount}
			<fmt:message key="efetionmanage.common.records" />:
			
			<span class="a_htblue">${pageInfo.page}</span>/
			<span class="a_htblue">${pageInfo.totalPage}</span>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<fmt:message key="efetionmanage.common.paginal" />
			${pageInfo.pageSize}
			
			<fmt:message key="efetionmanage.common.records" />
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</li>
		<!------------------------------------------------------------------------------>
      <li>
			<c:choose>
				<c:when test="${pageInfo.page <= 1 }">
					<a src="#">
					<fmt:message key="efetionmanage.common.first_page" />
				  </a>
				</c:when>
				<c:otherwise>
					<a href="#" onclick="JavaScript:goPage('1');" class="a_htblue">
						
						<fmt:message key="efetionmanage.common.first_page" />
						
					</a>
		    </c:otherwise>
			</c:choose>
			</li>
			<li>
			<c:choose>
				<c:when test="${pageInfo.page <= 1}">
					<a src="#">
					<fmt:message key="efetionmanage.common.previous_page" />
				</a>
				</c:when>
				<c:otherwise>
					<a href="#" onclick="JavaScript:goPage('${pageInfo.previousPage}');"
						class="a_htblue">
						
						<fmt:message key="efetionmanage.common.previous_page" />
						
					</a>
		    </c:otherwise>
			</c:choose>
			</li>
			<li>
			<c:choose>
				<c:when test="${pageInfo.page >= (pageInfo.totalPage)}">
					<a src="#">
					<fmt:message key="efetionmanage.common.next_page" />
					</a>
				</c:when>
				<c:otherwise>
					<a href="#" onclick="JavaScript:goPage('${pageInfo.nextPage}');" >
						
						<fmt:message key="efetionmanage.common.next_page" />
						
					</a> 
		    </c:otherwise>
			</c:choose>
			</li>
			<li>
			<c:choose>
				<c:when test="${pageInfo.page >= (pageInfo.totalPage)}">
					<a src="#" alt="aaa">
					<fmt:message key="efetionmanage.common.last_page" /></a>
				</c:when>
				<c:otherwise>
					<a href="#" onclick="JavaScript:goPage('${pageInfo.totalPage}');" > 
						
							<fmt:message key="efetionmanage.common.last_page" />

					</a> &nbsp;&nbsp;
		    </c:otherwise>
			</c:choose>
			</li>

		<!------------------------------------------------------------------------------>
		<li>
			<fmt:message key="efetionmanage.common.jump_to" />

			<input type="text" id="toPage" name="toPage" size="3" class="f-text"
				style="vertical-align:middle; font:11px/13px Tahoma;height:13px;overflow:hidden;" />

			<fmt:message key="efetionmanage.common.pages" />
			<a href="#" onclick="JavaScript:checkpage();">Go</a>
			</li>
			</ol>
		</td>
	</tr>
</table>
<script language="javascript">
function checkpage(){
   if(document.getElementById('toPage').value > ${pageInfo.totalPage}){
   //document.getElementById('toPage').style.backgroundColor='red';
   $.prompt('<fmt:message key="efetionmanage.common.outofpagetotal" />');
   document.getElementById('toPage').value="";
   }else if(document.getElementById('toPage').value == 0 ){
   //document.getElementById('toPage').style.backgroundColor='red';
   $.prompt('<fmt:message key="efetionmanage.common.pageiszero" />');
   document.getElementById('toPage').value="";
   }
   else{
   goPage(document.getElementById('toPage').value);
   }
   
}
</script>
