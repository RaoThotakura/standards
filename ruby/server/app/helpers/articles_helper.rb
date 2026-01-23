module ArticlesHelper
  def to_html (title,text)
      '<table>'.tap do |table|
			table << %(<tr><td class="name">#{CGI.escapeHTML(title.to_s)}</td>)
            table << %(<td class="value">#{CGI.escapeHTML(text.to_s)}</td></tr>)	
			table << '</table>'
        end
  end 
end
